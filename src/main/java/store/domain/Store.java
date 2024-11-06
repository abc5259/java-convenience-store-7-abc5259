package store.domain;

import java.util.Map;

public class Store {

    private final Map<Product, Integer> products;

    public Store(Map<Product, Integer> products) {
        this.products = products;
    }
}
