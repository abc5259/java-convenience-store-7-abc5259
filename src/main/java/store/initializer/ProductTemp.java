package store.initializer;

public record ProductTemp(
        String name,
        int price,
        int quantity,
        String promotionName
) {

    public boolean isNotPromotion() {
        return this.promotionName.equals("null");
    }
}
