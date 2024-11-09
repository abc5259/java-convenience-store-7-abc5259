package store.domain;

import java.time.LocalDate;

public class PromotionProduct {
    private static final int MINIMUM_QUANTITY = 0;

    private final Product product;
    private final Promotion promotion;
    private int quantity;

    public PromotionProduct(Product product, Promotion promotion, int quantity) {
        this.product = product;
        this.promotion = promotion;
        this.quantity = quantity;
    }

    public void validatePurchaseQuantity(int purchaseQuantity) {
        product.validatePurchaseQuantity(purchaseQuantity - this.quantity);
    }

    public boolean isNotApplicablePromotion(LocalDate now) {
        return !promotion.isApplicable(now);
    }

    public int calculateApplicablePromotionProductQuantity(int purchaseQuantity, LocalDate now) {
        validatePurchaseQuantity(purchaseQuantity);
        return promotion.calculateApplicablePromotionProductQuantity(this.quantity, purchaseQuantity, now);
    }


    public ProductPurchaseLog purchase(PurchaseItem purchaseItem, LocalDate now) {
        validatePurchaseQuantity(purchaseItem.getPurchaseQuantity());
        if (isNotApplicablePromotion(now)) {
            int purchaseQuantity = purchaseItem.getPurchaseQuantity();
            product.reduceQuantity(purchaseItem);
            reduceQuantity(purchaseItem);
            return new ProductPurchaseLog(0, 0, purchaseQuantity);
        }
        ProductPurchaseLog productPurchaseLog = createProductPurchaseLog(purchaseItem, now);
        reduceQuantity(purchaseItem);
        product.reduceQuantity(purchaseItem);
        return productPurchaseLog;
    }

    private ProductPurchaseLog createProductPurchaseLog(PurchaseItem purchaseItem, LocalDate now) {
        return new ProductPurchaseLog(
                promotion.calculateApplicablePromotionProductQuantity(
                        this.quantity,
                        purchaseItem.getPurchaseQuantity(),
                        now),
                promotion.calculateGiveawayProductQuantity(
                        this.quantity,
                        purchaseItem.getPurchaseQuantity(),
                        now),
                purchaseItem.getPurchaseQuantity());
    }

    private void reduceQuantity(PurchaseItem purchaseItem) {
        int currQuantity = this.quantity;
        this.quantity -= purchaseItem.getPurchaseQuantity();
        this.quantity = Math.max(MINIMUM_QUANTITY, this.quantity);
        purchaseItem.reduceQuantity(currQuantity);
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
