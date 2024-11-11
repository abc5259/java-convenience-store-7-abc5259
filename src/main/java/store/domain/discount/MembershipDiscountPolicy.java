package store.domain.discount;

import store.domain.Receipt;

public class MembershipDiscountPolicy implements DiscountPolicy {

    private static final int MAX_DISCOUNT_PRICE = 8000;
    private static final double DISCOUNT_AMOUNT = 0.3;

    private final DiscountCondition discountCondition;

    public MembershipDiscountPolicy(DiscountCondition discountCondition) {
        this.discountCondition = discountCondition;
    }

    @Override
    public int getDiscountPrice(Receipt receipt) {
        if (discountCondition.isSatisfiedBy()) {
            int discountPrice = (int) (receipt.getTotalNotApplicablePromotionProductPrice() * DISCOUNT_AMOUNT);
            return Math.min(discountPrice, MAX_DISCOUNT_PRICE);
        }
        return 0;
    }
}
