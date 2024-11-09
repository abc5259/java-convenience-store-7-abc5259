package store.domain;

import store.exception.NotEnoughQuantityException;

public class Product {

    private static final int MINIMUM_QUANTITY = 0;

    private final String name;
    private final int price;
    private int quantity;

    public Product(String name, int price) {
        this(name, price, MINIMUM_QUANTITY);
    }

    public Product(String name, int price, int quantity) {
        validateQuantityIsNegative(quantity);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void reduceQuantity(PurchaseItem purchaseItem) {
        int currentQuantity = this.quantity;
        this.quantity -= purchaseItem.getPurchaseQuantity();
        this.quantity = Math.max(this.quantity, MINIMUM_QUANTITY);
        purchaseItem.reduceQuantity(currentQuantity);
    }

    public ProductPurchaseLog purchase(PurchaseItem purchaseItem) {
        validatePurchaseQuantity(purchaseItem.getPurchaseQuantity());
        ProductPurchaseLog productPurchaseLog = new ProductPurchaseLog(0, 0, purchaseItem.getPurchaseQuantity());
        reduceQuantity(purchaseItem);
        return productPurchaseLog;
    }

    public void validatePurchaseQuantity(int count) {
        if (this.quantity < count) {
            throw new NotEnoughQuantityException();
        }
    }

    private void validateQuantityIsNegative(int quantity) {
        if (quantity < MINIMUM_QUANTITY) {
            throw new IllegalArgumentException("수량은 음수가 될 수 없습니다.");
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
