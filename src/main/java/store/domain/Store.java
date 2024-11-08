package store.domain;

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

    public PromotionNoticeResult calculatePromotionNoticeResult(String name, int purchaseQuantity) {
        validatePurchase(name, purchaseQuantity);
        Product product = findProductOrElseThrow(name);
        PromotionProduct productPromotion = productPromotions.get(product);

        if (productPromotion == null || productPromotion.isNotApplicablePromotion()) {
            return PromotionNoticeResult.notApplyPromotion(name);
        }

        int applicablePromotionProductQuantity =
                productPromotion.calculateApplicablePromotionProductQuantity(purchaseQuantity);
        return PromotionNoticeResult.from(name, purchaseQuantity, applicablePromotionProductQuantity);
    }

    public void validatePurchase(String name, int purchaseQuantity) {
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
