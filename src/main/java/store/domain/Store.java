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


    public List<Product> getProducts() {
        return List.copyOf(products.values());
    }

    public ProductPromotion getProductPromotion(Product product) {
        return productPromotions.get(product);
    }
}
