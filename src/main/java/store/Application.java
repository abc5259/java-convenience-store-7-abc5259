package store;

import java.util.Map;
import store.domain.Promotion;
import store.domain.Store;
import store.io.ProductsInitializer;
import store.io.PromotionsInitializer;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        OutputView outputView = new OutputView();
        outputView.printStartMessage();

        PromotionsInitializer promotionsInitializer = new PromotionsInitializer();
        Map<String, Promotion> promotions = promotionsInitializer.initialize();
        ProductsInitializer productsInitializer = new ProductsInitializer();
        Store store = productsInitializer.initialize(promotions);
        outputView.printProducts(store);

    }
}
