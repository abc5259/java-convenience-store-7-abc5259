package store.initializer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.Store;

public class StoreInitializer {

    private final Map<String, Promotion> promotions;
    private final List<TempProduct> tempProducts;

    public StoreInitializer(Map<String, Promotion> promotions, List<TempProduct> tempProducts) {
        this.promotions = promotions;
        this.tempProducts = tempProducts;
    }

    public Store initialize() {
        Map<String, Product> products = new LinkedHashMap<>();
        Map<Product, PromotionProduct> PromotionProducts = new LinkedHashMap<>();

        for (TempProduct tempProduct : tempProducts) {
            Product product = products.getOrDefault(
                    tempProduct.name(),
                    new Product(tempProduct.name(), tempProduct.price()));

            if (tempProduct.isNotPromotion()) {
                product.increaseQuantity(tempProduct.quantity());
                products.put(tempProduct.name(), product);
                continue;
            }

            Promotion promotion = promotions.get(tempProduct.promotionName());
            PromotionProducts.put(product, new PromotionProduct(product, promotion, tempProduct.quantity()));
            products.put(tempProduct.name(), product);
        }
        return new Store(products, PromotionProducts);
    }
}
