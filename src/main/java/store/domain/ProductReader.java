package store.domain;

import java.util.List;
import java.util.Map;
import store.exception.NonExistProductException;

public class ProductReader {

    private final Map<String, Product> products;

    public ProductReader(Map<String, Product> products) {
        this.products = products;
    }

    public Product findProductOrElseThrow(String name) {
        Product product = products.get(name);
        if (product == null) {
            throw new NonExistProductException();
        }
        return product;
    }

    public List<Product> findAllProducts() {
        return List.copyOf(products.values());
    }
}
