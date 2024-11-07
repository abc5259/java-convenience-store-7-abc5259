package store.domain;

import java.util.List;
import java.util.Map;

public class Store {

    private final Map<String, Product> products;
    private final Map<Product, ProductPromotion> productPromotions;

    public Store(Map<String, Product> products, Map<Product, ProductPromotion> productPromotions) {
        this.products = products;
        this.productPromotions = productPromotions;
    }

    public PromotionNoticeResult calculatePromotionNoticeResult(String name, int count) {
        validatePurchase(name, count);
        Product product = findProductOrElseThrow(name);
        ProductPromotion productPromotion = productPromotions.get(product);

        if (productPromotion == null || !productPromotion.isNotApplicablePromotion()) {
            return PromotionNoticeResult.Good();
        }

        productPromotion.
    }

    public void validatePurchase(String name, int count) {
        Product product = findProductOrElseThrow(name);
        ProductPromotion productPromotion = productPromotions.get(product);
        validatePurchaseQuantity(count, productPromotion, product);
    }

    private Product findProductOrElseThrow(String name) {
        Product product = products.get(name);
        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
        return product;
    }

    private void validatePurchaseQuantity(int count, ProductPromotion productPromotion, Product product) {
        if (productPromotion != null) {
            productPromotion.validatePurchaseQuantity(count);
            return;
        }

        product.validatePurchaseQuantity(count);
    }

    public List<Product> getProducts() {
        return List.copyOf(products.values());
    }

    public ProductPromotion getProductPromotion(Product product) {
        return productPromotions.get(product);
    }
}
