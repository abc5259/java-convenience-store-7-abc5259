package store.view;

import java.util.List;
import store.domain.Product;
import store.domain.ProductPromotion;
import store.domain.Store;

public class OutputView {
    private static final String NON_PROMOTION_PRODUCT_INFO_FORMAT = "- %s %,d원 %s%n";
    private static final String PROMOTION_PRODUCT_INFO_FORMAT = "- %s %,d원 %s %s%n";

    public void printStartMessage() {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.");
    }

    public void printProducts(Store store) {
        List<Product> products = store.getProducts();
        products.forEach(product -> printProduct(product, store.getProductPromotion(product)));
    }

    private void printProduct(Product product, ProductPromotion productPromotion) {
        if (productPromotion != null) {
            System.out.printf(PROMOTION_PRODUCT_INFO_FORMAT,
                    product.getName(),
                    product.getPrice(),
                    makeProductQuantityFormat(productPromotion.getQuantity()),
                    productPromotion.getPromotionName());
        }
        System.out.printf(NON_PROMOTION_PRODUCT_INFO_FORMAT,
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
