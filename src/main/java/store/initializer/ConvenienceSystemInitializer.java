package store.initializer;

import java.util.List;
import java.util.Map;
import store.domain.Promotion;
import store.domain.Store;

public class ConvenienceSystemInitializer {

    private final StoreInitializer storeInitializer;

    public ConvenienceSystemInitializer(StoreInitializer storeInitializer) {
        this.storeInitializer = storeInitializer;
    }

    public static ConvenienceSystemInitializer initialize(PromotionsInitializer promotionsInitializer,
                                                          ProductsInitializer productsInitializer) {
        Map<String, Promotion> promotions = promotionsInitializer.initialize();
        List<TempProduct> tempProducts = productsInitializer.initialize();
        return new ConvenienceSystemInitializer(new StoreInitializer(promotions, tempProducts));
    }

    public Store initStore() {
        return storeInitializer.initialize();
    }
}
