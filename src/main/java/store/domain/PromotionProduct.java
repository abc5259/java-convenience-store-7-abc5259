package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;

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

    public boolean isNotApplicablePromotion() {
        return !promotion.isApplicable(DateTimes.now().toLocalDate());
    }

    public int calculateApplicablePromotionProductQuantity(int purchaseQuantity) {
        validatePurchaseQuantity(purchaseQuantity);

        if (isNotApplicablePromotion()) {
            return 0;
        }

        return promotion.calculateApplicablePromotionProductQuantity(this.quantity, purchaseQuantity);
    }


    public ProductPurchaseLog purchase(PurchaseItem purchaseItem) {
        validatePurchaseQuantity(purchaseItem.getPurchaseQuantity());
        if (isNotApplicablePromotion()) {
            int purchaseQuantity = purchaseItem.getPurchaseQuantity();
            product.reduceQuantity(purchaseItem);
            reduceQuantity(purchaseItem);
            return new ProductPurchaseLog(0, 0, purchaseQuantity);
        }
        ProductPurchaseLog productPurchaseLog = createProductPurchaseLog(purchaseItem);
        reduceQuantity(purchaseItem);
        product.reduceQuantity(purchaseItem);
        return productPurchaseLog;
    }

    private ProductPurchaseLog createProductPurchaseLog(PurchaseItem purchaseItem) {
        int applicablePromotionProductQuantity = promotion.calculateApplicablePromotionProductQuantity(
                this.quantity,
                purchaseItem.getPurchaseQuantity());
        int giveawayProductQuantity = promotion.calculateGiveawayProductQuantity(
                this.quantity,
                purchaseItem.getPurchaseQuantity());
        return new ProductPurchaseLog(
                applicablePromotionProductQuantity,
                giveawayProductQuantity,
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
