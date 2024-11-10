package store;

import static store.domain.Answer.NO;
import static store.domain.Answer.YES;
import static store.domain.PromotionNoticeType.MORE_QUANTITY;
import static store.domain.PromotionNoticeType.NOT_APPLIED_QUANTITY;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import java.util.Map;
import store.domain.Answer;
import store.domain.Promotion;
import store.domain.PromotionNoticeResult;
import store.domain.PurchaseItem;
import store.domain.Receipt;
import store.domain.Store;
import store.io.ProductsInitializer;
import store.io.PromotionsInitializer;
import store.view.OutputView;

public class ConvenienceSystemRunner {
    private final IteratorInputHandler iteratorInputHandler;
    private final OutputView outputView;

    public ConvenienceSystemRunner(IteratorInputHandler iteratorInputHandler,
                                   OutputView outputView) {
        this.iteratorInputHandler = iteratorInputHandler;
        this.outputView = outputView;
    }

    public void run() {
        Store store = initStore();
        do {
            processConvenienceSystem(store);
        } while ((iteratorInputHandler.inputMorePurchaseProducts()).toBoolean());
    }

    private Store initStore() {
        PromotionsInitializer promotionsInitializer = new PromotionsInitializer();
        Map<String, Promotion> promotions = promotionsInitializer.initialize();
        ProductsInitializer productsInitializer = new ProductsInitializer();
        return productsInitializer.initialize(promotions);
    }

    private void processConvenienceSystem(Store store) {
        printStartMessages(store);

        Map<PurchaseItem, PromotionNoticeResult> promotionNoticeResults =
                iteratorInputHandler.calculatePromotionNoticeResults(store);
        promotionNoticeResults.forEach(this::noticePromotionResult);

        Answer membershipDiscountAnswer = iteratorInputHandler.inputMembershipDiscountRequest();

        printReceipt(promotionNoticeResults, store, membershipDiscountAnswer);
    }

    private void printStartMessages(Store store) {
        outputView.printStartMessage();
        outputView.printProducts(store);
    }

    private void noticePromotionResult(PurchaseItem purchaseItem, PromotionNoticeResult promotionNoticeResult) {
        if (promotionNoticeResult.promotionNoticeType() == MORE_QUANTITY) {
            Answer answer = iteratorInputHandler.inputExtraPromotionNoticeResult(promotionNoticeResult);
            if (answer == YES) {
                purchaseItem.increaseQuantity(promotionNoticeResult.productQuantity());
            }
        }
        if (promotionNoticeResult.promotionNoticeType() == NOT_APPLIED_QUANTITY) {
            Answer answer = iteratorInputHandler.inputExtraPromotionNoticeResult(promotionNoticeResult);
            if (answer == NO) {
                purchaseItem.reduceQuantity(promotionNoticeResult.productQuantity());
            }
        }
    }

    private void printReceipt(Map<PurchaseItem, PromotionNoticeResult> promotionNoticeResults,
                              Store store,
                              Answer membershipDiscountAnswer) {
        List<PurchaseItem> purchaseItems = promotionNoticeResults.keySet().stream().toList();
        Receipt receipt = store.purchaseProducts(
                purchaseItems,
                membershipDiscountAnswer.toBoolean(),
                DateTimes.now().toLocalDate());
        outputView.printReceipt(receipt);
    }
}
