package store.domain;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.exception.NonExistProductException;

public class Store {

    private final Map<String, Product> products;
    private final Map<Product, PromotionProduct> productPromotions;

    public Store(Map<String, Product> products, Map<Product, PromotionProduct> productPromotions) {
        this.products = products;
        this.productPromotions = productPromotions;
    }

    public Map<PurchaseItem, PromotionNoticeResult> calculatePromotionNoticeResults(List<PurchaseItem> purchaseItems,
                                                                                    LocalDate now) {
        Map<PurchaseItem, PromotionNoticeResult> promotionNoticeResultMap = new LinkedHashMap<>();
        for (PurchaseItem purchaseItem : purchaseItems) {
            PromotionNoticeResult promotionNoticeResult = calculatePromotionNoticeResult(
                    purchaseItem.getName(),
                    purchaseItem.getPurchaseQuantity(),
                    now);
            promotionNoticeResultMap.put(purchaseItem, promotionNoticeResult);
        }

        return promotionNoticeResultMap;
    }

    public PromotionNoticeResult calculatePromotionNoticeResult(String name, int purchaseQuantity, LocalDate now) {
        validatePurchase(name, purchaseQuantity);
        Product product = findProductOrElseThrow(name);
        PromotionProduct productPromotion = productPromotions.get(product);

        if (productPromotion == null || productPromotion.isNotApplicablePromotion(now)) {
            return PromotionNoticeResult.notApplyPromotion(name);
        }

        int adjustedPromotionProductQuantity =
                productPromotion.calculateAdjustedPromotionQuantity(purchaseQuantity, now);
        return PromotionNoticeResult.from(name, purchaseQuantity, adjustedPromotionProductQuantity);
    }

    public Receipt purchaseProducts(List<PurchaseItem> purchaseItems, boolean isApplicableMembership,
                                    LocalDate purchaseDate) {
        List<ProductPurchaseLog> productPurchaseLogs = purchaseItems.stream()
                .map(purchaseItem -> purchaseProduct(purchaseItem, purchaseDate))
                .toList();
        return new Receipt(productPurchaseLogs, isApplicableMembership);
    }

    private ProductPurchaseLog purchaseProduct(PurchaseItem purchaseItem, LocalDate purchaseDate) {
        validatePurchase(purchaseItem.getName(), purchaseItem.getPurchaseQuantity());
        Product product = findProductOrElseThrow(purchaseItem.getName());
        PromotionProduct productPromotion = productPromotions.get(product);

        if (productPromotion != null) {
            return productPromotion.purchase(purchaseItem, purchaseDate);
        }

        return product.purchase(purchaseItem);
    }

    private void validatePurchase(String name, int purchaseQuantity) {
        Product product = findProductOrElseThrow(name);
        PromotionProduct productPromotion = productPromotions.get(product);
        validatePurchaseQuantity(purchaseQuantity, productPromotion, product);
    }

    private Product findProductOrElseThrow(String name) {
        Product product = products.get(name);
        if (product == null) {
            throw new NonExistProductException();
        }
        return product;
    }

    private void validatePurchaseQuantity(int purchaseQuantity, PromotionProduct productPromotion, Product product) {
        if (productPromotion != null) {
            productPromotion.validatePurchaseQuantity(purchaseQuantity);
            return;
        }

        product.validatePurchaseQuantity(purchaseQuantity);
    }

    public List<Product> getProducts() {
        return List.copyOf(products.values());
    }

    public PromotionProduct getPromotionProduct(Product product) {
        return productPromotions.get(product);
    }
}
