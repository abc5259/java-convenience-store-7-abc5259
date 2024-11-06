package store.domain;

import java.util.List;

public class Store {

    private final List<Product> products;

    public Store(List<Product> products) {
        this.products = products;
    }

    public void purchase(OrderItem orderItem) {
        List<Product> sameNameProducts = findSameNameProducts(orderItem);
        if (sameNameProducts.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        int totalQuantity = sameNameProducts.stream().mapToInt(Product::getQuantity).sum();
        orderItem.validateCount(totalQuantity);

        List<Product> promotionProducts = sameNameProducts.stream()
                .filter(Product::isPromotionProduct)
                .toList();
        List<Product> simpleProducts = sameNameProducts.stream()
                .filter(product -> !product.isPromotionProduct())
                .toList();

        for (Product promotionProduct : promotionProducts) {
            promotionProduct.reduceQuantity(orderItem);
        }

        for (Product simpleProduct : simpleProducts) {
            simpleProduct.reduceQuantity(orderItem);
        }
    }

    private List<Product> findSameNameProducts(OrderItem orderItem) {
        return products.stream()
                .filter(product -> product.isSameName(orderItem))
                .toList();
    }
}
