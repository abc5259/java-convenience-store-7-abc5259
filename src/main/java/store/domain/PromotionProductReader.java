package store.domain;

import java.util.Map;
import java.util.Optional;

public class PromotionProductReader {

    private final Map<Product, PromotionProduct> productPromotions;

    public PromotionProductReader(Map<Product, PromotionProduct> productPromotions) {
        this.productPromotions = productPromotions;
    }

    public Optional<PromotionProduct> findPromotionProduct(Product product) {
        return Optional.ofNullable(productPromotions.get(product));
    }
}
