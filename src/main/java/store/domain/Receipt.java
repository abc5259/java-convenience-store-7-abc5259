package store.domain;

import java.util.List;

public class Receipt {
    private final List<ProductPurchaseLog> productPurchaseLogs;
    private final boolean isApplicableMemberShip;

    public Receipt(List<ProductPurchaseLog> productPurchaseLogs, boolean isApplicableMemberShip) {
        this.productPurchaseLogs = productPurchaseLogs;
        this.isApplicableMemberShip = isApplicableMemberShip;
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
        if (!isApplicableMemberShip) {
            return 0;
        }

        int discountPrice = (int) (getTotalNotApplicablePromotionProductPrice() * 0.3);
        return Math.min(discountPrice, 8000);
    }

    public int getLastPrice() {
        //TODO: 멤버십 할일해야함
        return getTotalPrice() - getTotalGiveawayProductPrice() - getMembershipDiscountPrice();
    }

    public List<ProductPurchaseLog> getProductPurchaseLogs() {
        return List.copyOf(productPurchaseLogs);
    }
}
