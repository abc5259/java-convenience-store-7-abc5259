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
            throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다.");
        }

        if (purchaseQuantity <= 0) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    public void increaseQuantity(int quantity) {
        this.purchaseQuantity += quantity;
    }

    public void increaseQuantity(PurchaseItem purchaseItem) {
        if (purchaseItem.isSameName(purchaseItem)) {
            this.purchaseQuantity += purchaseItem.purchaseQuantity;
        }
    }

    public void reduceQuantity(int quantity) {
        this.purchaseQuantity -= quantity;
        this.purchaseQuantity = Math.max(0, this.purchaseQuantity);
    }

    public String getName() {
        return name;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public boolean isSameName(PurchaseItem purchaseItem) {
        return this.name.equals(purchaseItem.getName());
    }
}
