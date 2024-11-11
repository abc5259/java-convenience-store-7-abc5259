package store.domain;

import java.util.List;
import store.domain.discount.MembershipDiscountPolicy;

public class Receipt {
    private final List<ProductPurchaseLog> productPurchaseLogs;
    private final MembershipDiscountPolicy membershipDiscountPolicy;

    public Receipt(List<ProductPurchaseLog> productPurchaseLogs, MembershipDiscountPolicy membershipDiscountPolicy) {
        this.productPurchaseLogs = productPurchaseLogs;
        this.membershipDiscountPolicy = membershipDiscountPolicy;
    }

    public int getTotalQuantity() {
        return productPurchaseLogs.stream()
                .mapToInt(ProductPurchaseLog::purchaseQuantity)
                .sum();
    }

    public int getTotalPrice() {
        return productPurchaseLogs.stream()
                .mapToInt(ProductPurchaseLog::calculateTotalPrice)
                .sum();
    }

    public int getTotalGiveawayProductPrice() {
        return productPurchaseLogs.stream()
                .mapToInt(ProductPurchaseLog::calculateGiveawayProductPrice)
                .sum();
    }

    public int getTotalNotApplicablePromotionProductPrice() {
        return productPurchaseLogs.stream()
                .mapToInt(ProductPurchaseLog::calculateNotApplicablePromotionProductPrice)
                .sum();
    }

    public int getMembershipDiscountPrice() {
        return membershipDiscountPolicy.getDiscountPrice(this);
    }

    public int getLastPrice() {
        return getTotalPrice() - getTotalGiveawayProductPrice() - getMembershipDiscountPrice();
    }

    public List<ProductPurchaseLog> getProductPurchaseLogs() {
        return List.copyOf(productPurchaseLogs);
    }
}
