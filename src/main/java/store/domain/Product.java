package store.domain;

import store.exception.NotEnoughQuantityException;

public class Product {

    private final String name;
    private final int price;
    private int quantity;

    public Product(String name, int price) {
        this(name, price, 0);
    }

    public Product(String name, int price, int quantity) {
        validateQuantityIsNegative(quantity);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    private void validateQuantityIsNegative(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량은 음수가 될 수 없습니다.");
        }
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void validatePurchaseQuantity(int count) {
        if (this.quantity < count) {
            throw new NotEnoughQuantityException();
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
