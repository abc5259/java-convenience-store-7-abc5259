package store.view;

import java.text.DecimalFormat;
import java.util.List;
import store.domain.Product;
import store.domain.ProductPurchaseLog;
import store.domain.PromotionProduct;
import store.domain.Receipt;
import store.domain.Store;

public class OutputView {
    private static final String PRODUCT_INFO_FORMAT = "- %s %,d원 %s%n";
    private static final String PROMOTION_PRODUCT_INFO_FORMAT = "- %s %,d원 %s %s%n";
    private static final String ERROR_MESSAGE_FORMAT = "[ERROR] %s 다시 입력해 주세요.%n";
    private static final String RECEIPT_FORMAT = "%-16s %-6s %-10s%n";
    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###");

    public void printStartMessage() {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.");
        printEmptyLine();
    }

    public void printProducts(Store store) {
        List<Product> products = store.getProducts();
        products.forEach(product -> printProduct(product, store.getPromotionProduct(product)));
        printEmptyLine();
    }

    public void printReceipt(Receipt receipt) {
        List<ProductPurchaseLog> productPurchaseLogs = receipt.getProductPurchaseLogs();
        printPurchaseProducts(productPurchaseLogs);
        printGiveawayProducts(productPurchaseLogs);
        printPrice(receipt);
    }

    private void printPurchaseProducts(List<ProductPurchaseLog> productPurchaseLogs) {
        System.out.println("==============W 편의점================");
        System.out.printf(RECEIPT_FORMAT, "금액", "수량", "상품명");
        productPurchaseLogs.forEach(purchaseLog -> {
            System.out.printf(RECEIPT_FORMAT,
                    purchaseLog.getProductName(),
                    purchaseLog.purchaseQuantity(),
                    purchaseLog.calculateTotalPrice());
        });
    }

    private void printGiveawayProducts(List<ProductPurchaseLog> productPurchaseLogs) {
        System.out.println("=============증\t\t정===============");
        productPurchaseLogs.forEach(purchaseLog -> {
            if (purchaseLog.giveawayProductQuantity() == 0) {
                return;
            }
            System.out.printf(RECEIPT_FORMAT, purchaseLog.getProductName(),
                    purchaseLog.giveawayProductQuantity(), "");
        });
    }

    private void printPrice(Receipt receipt) {
        System.out.println("====================================");
        System.out.printf(RECEIPT_FORMAT, "총구매액", receipt.getTotalQuantity(),
                decimalFormat.format(receipt.getTotalPrice()));
        System.out.printf(RECEIPT_FORMAT, "행사할인", "",
                "-" + decimalFormat.format(receipt.getTotalGiveawayProductPrice()));
        System.out.printf(RECEIPT_FORMAT, "멤버십할인", "",
                "-" + decimalFormat.format(receipt.getMembershipDiscountPrice()));
        System.out.printf(RECEIPT_FORMAT, "내실돈", "", decimalFormat.format(receipt.getLastPrice()));
    }

    public void printErrorMessage(String message) {
        System.out.printf(ERROR_MESSAGE_FORMAT, message);
    }

    private void printEmptyLine() {
        System.out.println();
    }

    private void printProduct(Product product, PromotionProduct productPromotion) {
        if (productPromotion != null) {
            System.out.printf(PROMOTION_PRODUCT_INFO_FORMAT,
                    product.getName(),
                    product.getPrice(),
                    makeProductQuantityFormat(productPromotion.getQuantity()),
                    productPromotion.getPromotionName());
        }
        System.out.printf(PRODUCT_INFO_FORMAT,
                product.getName(),
                product.getPrice(),
                makeProductQuantityFormat(product.getQuantity()));
    }

    private String makeProductQuantityFormat(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return quantity + "개";
    }
}
