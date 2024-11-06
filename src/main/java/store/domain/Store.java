package store.domain;

import java.util.Map;

public class Store {

    private final Map<Product, Integer> products;

    public Store(Map<Product, Integer> products) {
        validateProductCountIsNotNegative(products);
        this.products = products;
    }

    private void validateProductCountIsNotNegative(Map<Product, Integer> products) {
        if (isExistsNegativeCountProduct(products)) {
            throw new IllegalArgumentException("상품의 재고는 음수가 될 수 없습니다.");
        }
    }

    private boolean isExistsNegativeCountProduct(Map<Product, Integer> products) {
        return products.values().stream()
                .anyMatch(count -> count < 0);
    }
}
