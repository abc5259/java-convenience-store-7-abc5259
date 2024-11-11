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
        init(products, PromotionProducts);
        return new Store(products, PromotionProducts);
    }

    private void init(Map<String, Product> products, Map<Product, PromotionProduct> PromotionProducts) {
        for (TempProduct tempProduct : tempProducts) {
            Product product = computeProducts(products, tempProduct);
            computePromotionProducts(PromotionProducts, tempProduct, product);
        }
    }

    private Product computeProducts(Map<String, Product> products, TempProduct tempProduct) {
        return products.computeIfAbsent(
                tempProduct.name(),
                name -> new Product(name, tempProduct.price()));
    }

    private void computePromotionProducts(Map<Product, PromotionProduct> PromotionProducts,
                                          TempProduct tempProduct,
                                          Product product) {
        if (tempProduct.isNotPromotion()) {
            product.increaseQuantity(tempProduct.quantity());
            return;
        }

        Promotion promotion = promotions.get(tempProduct.promotionName());
        PromotionProducts.put(product, new PromotionProduct(product, promotion, tempProduct.quantity()));
    }
}
