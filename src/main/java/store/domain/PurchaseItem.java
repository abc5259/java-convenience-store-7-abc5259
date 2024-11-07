package store.domain;

public record PurchaseItem(
        String name,
        int purchaseQuantity
) {

    public PurchaseItem {
        validate(name, purchaseQuantity);
    }

    private void validate(String name, int purchaseQuantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }

        if (purchaseQuantity < 0) {
            throw new IllegalArgumentException("구매 수량은 0보다 커야합니다.");
        }
    }
}
