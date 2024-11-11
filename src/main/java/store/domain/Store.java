package store.domain;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.discount.AnswerDiscountCondition;
import store.domain.discount.MembershipDiscountPolicy;

public class Store {

    private final ProductReader productReader;
    private final Map<Product, PromotionProduct> productPromotions;

    public Store(Map<String, Product> products, Map<Product, PromotionProduct> productPromotions) {
        this.productReader = new ProductReader(products);
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
        Product product = productReader.findProductOrElseThrow(name);
        PromotionProduct productPromotion = productPromotions.get(product);

        if (productPromotion == null || productPromotion.isNotApplicablePromotion(now)) {
            return PromotionNoticeResult.notApplyPromotion(name);
        }

        int adjustedPromotionProductQuantity =
                productPromotion.calculateAdjustedPromotionQuantity(purchaseQuantity, now);
        return PromotionNoticeResult.from(name, purchaseQuantity, adjustedPromotionProductQuantity);
    }

    public Receipt purchaseProducts(List<PurchaseItem> purchaseItems, Answer applicableMembershipAnswer,
                                    LocalDate purchaseDate) {
        List<ProductPurchaseLog> productPurchaseLogs = purchaseItems.stream()
                .map(purchaseItem -> purchaseProduct(purchaseItem, purchaseDate))
                .toList();
        return new Receipt(productPurchaseLogs,
                new MembershipDiscountPolicy(new AnswerDiscountCondition(applicableMembershipAnswer)));
    }

    private ProductPurchaseLog purchaseProduct(PurchaseItem purchaseItem, LocalDate purchaseDate) {
        validatePurchase(purchaseItem.getName(), purchaseItem.getPurchaseQuantity());
        Product product = productReader.findProductOrElseThrow(purchaseItem.getName());
        PromotionProduct productPromotion = productPromotions.get(product);

        if (productPromotion != null) {
            return productPromotion.purchase(purchaseItem, purchaseDate);
        }

        return product.purchase(purchaseItem);
    }

    private void validatePurchase(String name, int purchaseQuantity) {
        Product product = productReader.findProductOrElseThrow(name);
        PromotionProduct productPromotion = productPromotions.get(product);
        validatePurchaseQuantity(purchaseQuantity, productPromotion, product);
    }

    private void validatePurchaseQuantity(int purchaseQuantity, PromotionProduct productPromotion, Product product) {
        if (productPromotion != null) {
            productPromotion.validatePurchaseQuantity(purchaseQuantity);
            return;
        }

        product.validatePurchaseQuantity(purchaseQuantity);
    }

    public List<Product> getProducts() {
        return productReader.findAllProducts();
    }

    public PromotionProduct getPromotionProduct(Product product) {
        return productPromotions.get(product);
    }
}
