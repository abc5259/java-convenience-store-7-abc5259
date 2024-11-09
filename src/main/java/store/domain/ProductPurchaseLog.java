package store.domain;

public record ProductPurchaseLog(
        ProductInfo productInfo,
        int applicablePromotionProductQuantity,
        int giveawayProductQuantity,
        int purchaseQuantity
) {
}
