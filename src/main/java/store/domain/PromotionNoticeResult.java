package store.domain;

import static store.domain.PromotionNoticeType.EXACT_PROMOTION;
import static store.domain.PromotionNoticeType.MORE_QUANTITY;
import static store.domain.PromotionNoticeType.NOT_APPLIED_PROMOTION;
import static store.domain.PromotionNoticeType.NOT_APPLIED_QUANTITY;

public record PromotionNoticeResult(
        PromotionNoticeType type,
        String productName,
        int productQuantity
) {

    public static PromotionNoticeResult notApplyPromotion(String name) {
        return new PromotionNoticeResult(NOT_APPLIED_PROMOTION, name, 0);
    }

    public static PromotionNoticeResult from(String name,
                                             int purchaseQuantity,
                                             int applicablePromotionProductQuantity) {
        if (purchaseQuantity == applicablePromotionProductQuantity) {
            return new PromotionNoticeResult(EXACT_PROMOTION, name, purchaseQuantity);
        }

        if (purchaseQuantity < applicablePromotionProductQuantity) {
            return new PromotionNoticeResult(MORE_QUANTITY, name,
                    applicablePromotionProductQuantity - purchaseQuantity);
        }

        return new PromotionNoticeResult(NOT_APPLIED_QUANTITY, name,
                purchaseQuantity - applicablePromotionProductQuantity);
    }
}
