package store.domain;

public record ProductPurchaseLog(
        int applicablePromotionProductQuantity,
        int giveawayProductQuantity,
        int totalQuantity
) {
}
