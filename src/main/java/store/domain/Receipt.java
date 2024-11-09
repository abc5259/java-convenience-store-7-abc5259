package store.domain;

import java.util.List;

public class Receipt {
    private final List<ProductPurchaseLog> productPurchaseLogs;

    public Receipt(List<ProductPurchaseLog> productPurchaseLogs) {
        this.productPurchaseLogs = productPurchaseLogs;
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

    public int getLastPrice() {
        //TODO: 멤버십 할일해야함
        return getTotalPrice() - getTotalGiveawayProductPrice();
    }
}
