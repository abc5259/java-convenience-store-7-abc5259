package store.domain;

public class ProductPromotion {
    private final Product product;
    private final Promotion promotion;
    private final int quantity;

    public ProductPromotion(Product product, Promotion promotion, int quantity) {
        this.product = product;
        this.promotion = promotion;
        this.quantity = quantity;
    }

    public String getPromotionName() {
        return promotion.getName();
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "ProductPromotion{" +
                "product=" + product +
                ", promotion=" + promotion +
                ", quantity=" + quantity +
                '}';
    }
}
