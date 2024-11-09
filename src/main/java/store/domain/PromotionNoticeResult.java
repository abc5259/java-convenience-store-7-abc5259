package store.domain;

import static store.domain.PromotionNoticeType.EXACT_PROMOTION;
import static store.domain.PromotionNoticeType.MORE_QUANTITY;
import static store.domain.PromotionNoticeType.NOT_APPLIED_PROMOTION;
import static store.domain.PromotionNoticeType.NOT_APPLIED_QUANTITY;

public record PromotionNoticeResult(
        PromotionNoticeType promotionNoticeType,
        String productName,
        int productQuantity
) {

    public static PromotionNoticeResult notApplyPromotion(String name) {
        return new PromotionNoticeResult(NOT_APPLIED_PROMOTION, name, 0);
    }

    public static PromotionNoticeResult from(String name,
                                             int purchaseQuantity,
                                             int adjustedPromotionProductQuantity) {
        if (purchaseQuantity == adjustedPromotionProductQuantity) {
            return new PromotionNoticeResult(EXACT_PROMOTION, name, purchaseQuantity);
        }

        if (purchaseQuantity < adjustedPromotionProductQuantity) {
            return new PromotionNoticeResult(MORE_QUANTITY, name,
                    adjustedPromotionProductQuantity - purchaseQuantity);
        }

        return new PromotionNoticeResult(NOT_APPLIED_QUANTITY, name,
                purchaseQuantity - adjustedPromotionProductQuantity);
    }
}
