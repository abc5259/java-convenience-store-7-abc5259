package store.domain;

public record PromotionNoticeResult(
        PromotionNoticeType type,
        String productName,
        int productQuantity
) {

    public static PromotionNoticeResult Good() {
        return new PromotionNoticeResult(PromotionNoticeType.GOOD, null, 0);
    }
}
