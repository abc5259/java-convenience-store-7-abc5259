package store.domain;

import store.exception.ErrorMessage;

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
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT_MESSAGE.getMessage());
        }

        if (purchaseQuantity <= 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_MESSAGE.getMessage());
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
