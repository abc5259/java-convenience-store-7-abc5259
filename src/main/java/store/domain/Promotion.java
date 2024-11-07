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
     * @param promotionProductQuantity
     * @param purchaseQuantity
     * @return 얼만만큼의 수량이 혜택을 받는지 리턴
     */
    public int calculateApplicablePromotionProductQuantity(int promotionProductQuantity, int purchaseQuantity) {
        purchaseQuantity = Math.min(purchaseQuantity, promotionProductQuantity);
        int rest = purchaseQuantity % (buyCount + getCount);
        System.out.println("rest = " + rest);
        if (rest == 0) {
            return purchaseQuantity; //구매 수량만큼 모두 프로모션 적용 가능 ALL, purchaseQuantity
        }
        if (rest == buyCount && purchaseQuantity + getCount <= promotionProductQuantity) {
            return purchaseQuantity + getCount; // 더 들고와 getCount만큼 MORE, purchaseQuantity + getCount
        }
        return purchaseQuantity - rest; // 야 좀 빼야겠는데 다는 못해주겠고 이정도 빼야해 // LITTLE, purchaseQuantity - rest
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
