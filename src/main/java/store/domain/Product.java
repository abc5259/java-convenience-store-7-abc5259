package store.domain;

public class Product {

    private final String name;
    private final int price;
    private int quantity;
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

    public boolean isSameName(OrderItem orderItem) {
        return name.equals(orderItem.getName());
    }

    public boolean isPromotionProduct() {
        return this.promotion != null;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity(OrderItem orderItem) {
        int count = orderItem.getCount();

        orderItem.decreaseCount(this.quantity);
        this.quantity = Math.max(0, this.quantity - count);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", promotion=" + (promotion == null ? "null" : promotion.getName()) +
                '}';
    }
}
