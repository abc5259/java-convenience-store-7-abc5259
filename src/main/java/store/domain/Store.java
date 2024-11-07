package store.domain;

import java.util.List;
import java.util.Map;
import store.exception.NonExistProductException;

public class Store {

    private final Map<String, Product> products;
    private final Map<Product, ProductPromotion> productPromotions;

    public Store(Map<String, Product> products, Map<Product, ProductPromotion> productPromotions) {
        this.products = products;
        this.productPromotions = productPromotions;
    }

    public PromotionNoticeResult calculatePromotionNoticeResult(String name, int purchaseQuantity) {
        validatePurchase(name, purchaseQuantity);
        Product product = findProductOrElseThrow(name);
        ProductPromotion productPromotion = productPromotions.get(product);

        if (productPromotion == null || productPromotion.isNotApplicablePromotion()) {
            return PromotionNoticeResult.notApplyPromotion(name);
        }

        int applicablePromotionProductQuantity = productPromotion.calculateApplicablePromotionProductQuantity(
                purchaseQuantity);
        return PromotionNoticeResult.from(name, purchaseQuantity, applicablePromotionProductQuantity);
    }

    public void validatePurchase(String name, int purchaseQuantity) {
        Product product = findProductOrElseThrow(name);
        ProductPromotion productPromotion = productPromotions.get(product);
        validatePurchaseQuantity(purchaseQuantity, productPromotion, product);
    }

    private Product findProductOrElseThrow(String name) {
        Product product = products.get(name);
        if (product == null) {
            throw new NonExistProductException();
        }
        return product;
    }

    private void validatePurchaseQuantity(int purchaseQuantity, ProductPromotion productPromotion, Product product) {
        if (productPromotion != null) {
            productPromotion.validatePurchaseQuantity(purchaseQuantity);
            return;
        }

        product.validatePurchaseQuantity(purchaseQuantity);
    }

    public List<Product> getProducts() {
        return List.copyOf(products.values());
    }

    public ProductPromotion getProductPromotion(Product product) {
        return productPromotions.get(product);
    }
}
