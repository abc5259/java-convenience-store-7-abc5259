package store.domain;

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

    public boolean isSameName(OrderItem orderItem) {
        return name.equals(orderItem.getName());
    }

    public void reduceQuantity(OrderItem orderItem) {
        int count = orderItem.getCount();

        orderItem.decreaseCount(this.quantity);
        this.quantity = Math.max(0, this.quantity - count);
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
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
