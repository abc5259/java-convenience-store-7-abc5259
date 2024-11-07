package store;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Promotion;
import store.io.ProductsInitializer;
import store.io.PromotionsInitializer;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) throws IOException {
        // TODO: 프로그램 구현
        OutputView outputView = new OutputView();
        outputView.printStartMessage();

        PromotionsInitializer promotionsInitializer = new PromotionsInitializer();
        Map<String, Promotion> promotions = promotionsInitializer.initialize();
        ProductsInitializer productsInitializer = new ProductsInitializer();
        List<Product> products = productsInitializer.initialize(promotions);
        System.out.println(products);

    }
}
