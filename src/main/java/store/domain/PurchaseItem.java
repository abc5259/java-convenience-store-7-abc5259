package store.domain;

public class PurchaseItem {
    private final String name;
    private int purchaseQuantity;

    public PurchaseItem(String name, int purchaseQuantity) {
        validate(name, purchaseQuantity);
        this.name = name;
        this.purchaseQuantity = purchaseQuantity;
    }

    private void validate(String name, int purchaseQuantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }

        if (purchaseQuantity < 0) {
            throw new IllegalArgumentException("구매 수량은 0보다 커야합니다.");
        }
    }

    public void increaseQuantity(int quantity) {
        this.purchaseQuantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        this.purchaseQuantity -= quantity;
    }

    public String getName() {
        return name;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }
}