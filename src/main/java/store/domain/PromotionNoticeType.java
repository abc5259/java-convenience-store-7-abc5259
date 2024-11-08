package store.domain;

public enum PromotionNoticeType {
    EXACT_PROMOTION(false),
    MORE_QUANTITY(true),
    NOT_APPLIED_QUANTITY(true),
    NOT_APPLIED_PROMOTION(false),
    ;

    private final boolean isNeedExtraRequest;

    PromotionNoticeType(boolean isNeedExtraRequest) {
        this.isNeedExtraRequest = isNeedExtraRequest;
    }

    public boolean isNeedExtraRequest() {
        return isNeedExtraRequest;
    }
}
