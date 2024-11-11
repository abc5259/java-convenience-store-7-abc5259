package store.domain.discount;

import store.domain.Receipt;

public interface DiscountPolicy {
    int getDiscountPrice(Receipt receipt);
}
