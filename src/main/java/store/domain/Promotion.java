package store.domain;

import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int buyCount;
    private final int getCount;
    private final Period period;

    public Promotion(String name, int buyCount, int getCount, LocalDate startDate, LocalDate endDate) {
        this.name = name.trim();
        validate(buyCount, getCount);
        this.buyCount = buyCount;
        this.getCount = getCount;
        period = new Period(startDate, endDate);
    }

    private void validate(int buyCount, int getCount) {
        if (buyCount <= 0) {
            throw new IllegalArgumentException("buyCount는 0보다 커야합니다.");
        }

        if (getCount <= 0) {
            throw new IllegalArgumentException("getCount는 0보다 커야합니다.");
        }
    }

    public boolean isApplicable(LocalDate currentDate) {
        return period.isWithin(currentDate);
    }

    /**
     * @param promotionProductQuantity 현재 프로모션 상품 수량
     * @param purchaseQuantity         구매하기 원하는 상품 수량
     * @param now                      현재 날짜
     * @return 혜택을 받을 수 있는 최종 상품 수량.
     */
    public int calculateAdjustedPromotionQuantity(int promotionProductQuantity,
                                                  int purchaseQuantity,
                                                  LocalDate now) {
        if (!isApplicable(now)) {
            return 0;
        }
        purchaseQuantity = Math.min(purchaseQuantity, promotionProductQuantity);
        int remainder = purchaseQuantity % (buyCount + getCount);
        if (remainder == 0) {
            return purchaseQuantity;
        }

        if (canMorePurchaseQuantity(promotionProductQuantity, purchaseQuantity, remainder)) {
            return purchaseQuantity + getCount;
        }

        return purchaseQuantity - remainder;
    }

    /**
     * @param promotionProductQuantity 현재 프로모션 상품 수량
     * @param purchaseQuantity         구매하기 원하는 상품 수량
     * @return 얼만만큼의 수량이 혜택을 받는지 리턴
     */
    public int calculateApplicablePromotionProductQuantity(int promotionProductQuantity,
                                                           int purchaseQuantity,
                                                           LocalDate now) {
        if (!isApplicable(now)) {
            return 0;
        }
        purchaseQuantity = Math.min(purchaseQuantity, promotionProductQuantity);
        int remainder = purchaseQuantity % (buyCount + getCount);
        if (remainder == 0) {
            return purchaseQuantity;
        }

        return purchaseQuantity - remainder;
    }

    public int calculateGiveawayProductQuantity(int promotionProductQuantity, int purchaseQuantity, LocalDate now) {
        int applicablePromotionProductQuantity = calculateApplicablePromotionProductQuantity(
                promotionProductQuantity,
                purchaseQuantity,
                now);
        return (applicablePromotionProductQuantity / (buyCount + getCount)) * getCount;
    }

    private boolean canMorePurchaseQuantity(int promotionProductQuantity, int purchaseQuantity, int remainder) {
        return remainder == buyCount && purchaseQuantity + getCount <= promotionProductQuantity;
    }

    public String getName() {
        return name;
    }
}
