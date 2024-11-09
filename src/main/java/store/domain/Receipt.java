package store.domain;

import java.util.List;

public class Receipt {
    private final List<ProductPurchaseLog> productPurchaseLogs;

    public Receipt(List<ProductPurchaseLog> productPurchaseLogs) {
        this.productPurchaseLogs = productPurchaseLogs;
    }


}
