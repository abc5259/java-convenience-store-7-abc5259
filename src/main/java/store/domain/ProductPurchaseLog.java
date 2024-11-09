package store.domain;

public record ProductPurchaseLog(
        ProductInfo productInfo,
        int applicablePromotionProductQuantity,
        int giveawayProductQuantity,
        int purchaseQuantity
) {

    public int calculateTotalPrice() {
        return productInfo.price() * purchaseQuantity;
    }

    public int calculateGiveawayProductPrice() {
        return productInfo.price() * giveawayProductQuantity;
    }

    public int calculateNotApplicablePromotionProductPrice() {
        return productInfo.price() * (purchaseQuantity - applicablePromotionProductQuantity);
    }
}
