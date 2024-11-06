package store.domain;

public class Product {

    private final String name;
    private final int price;
    private final Promotion promotion;

    public Product(String name, int price, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
    }
}
