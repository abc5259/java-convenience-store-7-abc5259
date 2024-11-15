package store.view;

import java.text.DecimalFormat;
import java.util.List;
import store.domain.Product;
import store.domain.ProductPurchaseLog;
import store.domain.PromotionProduct;
import store.domain.Receipt;
import store.domain.Store;

public class OutputView {
    private static final String START_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.";
    private static final String NO_QUANTITY = "재고 없음";
    private static final String QUANTITY_UNIT = "개";
    private static final String RECEIPT_TITLE = "==============W 편의점================";
    private static final String RECEIPT_GIVEAWAY_TITLE = "=============증\t\t정===============";
    private static final String RECEIPT_PRICE_TITLE = "====================================";
    private static final String PRODUCT_INFO_FORMAT = "- %s %,d원 %s%n";
    private static final String PROMOTION_PRODUCT_INFO_FORMAT = "- %s %,d원 %s %s%n";
    private static final String ERROR_MESSAGE_FORMAT = "[ERROR] %s 다시 입력해 주세요.%n";
    private static final String RECEIPT_FORMAT = "%-16s\t%-6s\t%-10s%n";
    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###");
    private static final String MINUS = "-";

    public void printStartMessage() {
        System.out.println(START_MESSAGE);
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
        printEmptyLine();
        System.out.println(RECEIPT_TITLE);
        System.out.printf(RECEIPT_FORMAT, "상품명", "수량", "금액");
        productPurchaseLogs.forEach(purchaseLog -> {
            System.out.printf(RECEIPT_FORMAT,
                    purchaseLog.getProductName(),
                    decimalFormat.format(purchaseLog.purchaseQuantity()),
                    decimalFormat.format(purchaseLog.calculateTotalPrice()));
        });
    }

    private void printGiveawayProducts(List<ProductPurchaseLog> productPurchaseLogs) {
        System.out.println(RECEIPT_GIVEAWAY_TITLE);
        productPurchaseLogs.forEach(purchaseLog -> {
            if (purchaseLog.giveawayProductQuantity() == 0) {
                return;
            }
            System.out.printf(RECEIPT_FORMAT, purchaseLog.getProductName(),
                    decimalFormat.format(purchaseLog.giveawayProductQuantity()), "");
        });
    }

    private void printPrice(Receipt receipt) {
        System.out.println(RECEIPT_PRICE_TITLE);
        System.out.printf(RECEIPT_FORMAT, "총구매액", decimalFormat.format(receipt.getTotalQuantity()),
                decimalFormat.format(receipt.getTotalPrice()));
        System.out.printf(RECEIPT_FORMAT, "행사할인", "",
                MINUS + decimalFormat.format(receipt.getTotalGiveawayProductPrice()));
        System.out.printf(RECEIPT_FORMAT, "멤버십할인", "",
                MINUS + decimalFormat.format(receipt.getMembershipDiscountPrice()));
        System.out.printf(RECEIPT_FORMAT, "내실돈", "", " " + decimalFormat.format(receipt.getLastPrice()));
        printEmptyLine();
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
            return NO_QUANTITY;
        }
        return quantity + QUANTITY_UNIT;
    }
}