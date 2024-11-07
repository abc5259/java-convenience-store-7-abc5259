package store;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import store.converter.StringToPurchaseItemConverter;
import store.domain.Promotion;
import store.domain.PromotionNoticeResult;
import store.domain.PurchaseItem;
import store.domain.Store;
import store.io.ProductsInitializer;
import store.io.PromotionsInitializer;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceSystemRunner {

    private final InputView inputView;
    private final OutputView outputView;

    public ConvenienceSystemRunner(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Store store = initStore();
        outputView.printStartMessage();
        outputView.printProducts(store);

        List<PurchaseItem> purchaseItems = inputPurchaseItems();
        try {
            for (PurchaseItem purchaseItem : purchaseItems) {
                PromotionNoticeResult promotionNoticeResult = store.calculatePromotionNoticeResult(purchaseItem.name(),
                        purchaseItem.purchaseQuantity());
            }
        } catch (IllegalArgumentException exception) {
            outputView.printErrorMessage(exception);
        }
    }

    private Store initStore() {
        PromotionsInitializer promotionsInitializer = new PromotionsInitializer();
        Map<String, Promotion> promotions = promotionsInitializer.initialize();
        ProductsInitializer productsInitializer = new ProductsInitializer();
        return productsInitializer.initialize(promotions);
    }

    public List<PurchaseItem> inputPurchaseItems() {
        while (true) {
            try {
                String[] items = inputView.inputPurchaseItems().split(",");
                validateLength(items);
                StringToPurchaseItemConverter converter = new StringToPurchaseItemConverter();
                return Arrays.stream(items)
                        .map(String::trim)
                        .map(converter::convert)
                        .toList();
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage("올바르지 않은 형식으로 입력했습니다.");
            }
        }
    }

    private void validateLength(String[] items) {
        if (items.length == 0) {
            throw new IllegalArgumentException("값을 입력하세요");
        }
    }
}
