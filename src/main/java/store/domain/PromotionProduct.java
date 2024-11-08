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


    public void reduceQuantity(PurchaseItem purchaseItem) {
        if (isNotApplicablePromotion()) {
            product.reduceQuantity(purchaseItem);
            int currQuantity = this.quantity;
            this.quantity -= purchaseItem.getPurchaseQuantity();
            this.quantity = Math.max(MINIMUM_QUANTITY, this.quantity);
            purchaseItem.decreaseQuantity(currQuantity);
            return;
        }
        int currQuantity = this.quantity;
        this.quantity -= purchaseItem.getPurchaseQuantity();
        this.quantity = Math.max(MINIMUM_QUANTITY, this.quantity);
        purchaseItem.decreaseQuantity(currQuantity);
        product.reduceQuantity(purchaseItem);
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
