package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;

public class ProductPromotion {
    private final Product product;
    private final Promotion promotion;
    private final int quantity;

    public ProductPromotion(Product product, Promotion promotion, int quantity) {
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
