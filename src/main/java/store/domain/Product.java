package store.domain;

public class Product {

    private final String name;
    private final int price;
    private final int quantity;
    private final Promotion promotion;

    public Product(String name, int price, int quantity, Promotion promotion) {
        validateQuantityIsNegative(quantity);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    private void validateQuantityIsNegative(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량은 음수가 될 수 없습니다.");
        }
    }
}
