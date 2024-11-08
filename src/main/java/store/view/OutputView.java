package store.view;

import java.util.List;
import store.domain.Product;
import store.domain.PromotionProduct;
import store.domain.Store;

public class OutputView {
    private static final String PRODUCT_INFO_FORMAT = "- %s %,d원 %s%n";
    private static final String PROMOTION_PRODUCT_INFO_FORMAT = "- %s %,d원 %s %s%n";
    private static final String ERROR_MESSAGE_FORMAT = "[ERROR] %s 다시 입력해 주세요.%n";

    public void printStartMessage() {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.");
        printEmptyLine();
    }

    public void printProducts(Store store) {
        List<Product> products = store.getProducts();
        products.forEach(product -> printProduct(product, store.getPromotionProduct(product)));
        printEmptyLine();
    }

    public void printErrorMessage(RuntimeException exception) {
        System.out.printf(ERROR_MESSAGE_FORMAT, exception.getMessage());
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
