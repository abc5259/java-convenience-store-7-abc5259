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

    public int calculateAdjustedPromotionQuantity(int purchaseQuantity, LocalDate now) {
        validatePurchaseQuantity(purchaseQuantity);
        return promotion.calculateAdjustedPromotionQuantity(this.quantity, purchaseQuantity, now);
    }
    
    public ProductPurchaseLog purchase(PurchaseItem purchaseItem, LocalDate purchaseDate) {
        validatePurchaseQuantity(purchaseItem.getPurchaseQuantity());
        if (isNotApplicablePromotion(purchaseDate)) {
            int purchaseQuantity = purchaseItem.getPurchaseQuantity();
            product.reduceQuantity(purchaseItem);
            reduceQuantity(purchaseItem);
            return new ProductPurchaseLog(this.product.getProductInfo(), 0, 0, purchaseQuantity);
        }
        ProductPurchaseLog productPurchaseLog = createProductPurchaseLog(purchaseItem, purchaseDate);
        reduceQuantity(purchaseItem);
        product.reduceQuantity(purchaseItem);
        return productPurchaseLog;
    }

    private ProductPurchaseLog createProductPurchaseLog(PurchaseItem purchaseItem, LocalDate purchaseDate) {
        return new ProductPurchaseLog(
                this.product.getProductInfo(),
                promotion.calculateApplicablePromotionProductQuantity(
                        this.quantity,
                        purchaseItem.getPurchaseQuantity(),
                        purchaseDate),
                promotion.calculateGiveawayProductQuantity(
                        this.quantity,
                        purchaseItem.getPurchaseQuantity(),
                        purchaseDate),
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
}
