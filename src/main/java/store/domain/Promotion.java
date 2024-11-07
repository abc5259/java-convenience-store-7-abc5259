package store.domain;

import java.time.LocalDate;

public class Promotion {

    private String name;
    private int buyCount;
    private int getCount;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(String name, int buyCount, int getCount, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buyCount = buyCount;
        this.getCount = getCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isApplicable(LocalDate currentDate) {
        return isInDate(currentDate);
    }

    /**
     * @param promotionProductQuantity 현재 프로모션 상품 수량
     * @param purchaseQuantity         구매하기 원하는 상품 수량
     * @return 얼만만큼의 수량이 혜택을 받는지 리턴
     */
    public int calculateApplicablePromotionProductQuantity(int promotionProductQuantity, int purchaseQuantity) {
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

    private boolean canMorePurchaseQuantity(int promotionProductQuantity, int purchaseQuantity, int remainder) {
        return remainder == buyCount && purchaseQuantity + getCount <= promotionProductQuantity;
    }

    private boolean isInDate(LocalDate targetDate) {
        return !targetDate.isBefore(startDate) && !targetDate.isAfter(endDate);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "name='" + name + '\'' +
                ", buyCount=" + buyCount +
                ", getCount=" + getCount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
