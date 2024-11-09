package store;

import static store.domain.Answer.NO;
import static store.domain.Answer.YES;
import static store.domain.PromotionNoticeType.MORE_QUANTITY;
import static store.domain.PromotionNoticeType.NOT_APPLIED_QUANTITY;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import store.converter.StringToAnswerConverter;
import store.converter.StringToPurchaseItemConverter;
import store.domain.Answer;
import store.domain.Promotion;
import store.domain.PromotionNoticeResult;
import store.domain.PurchaseItem;
import store.domain.Receipt;
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
        Answer gameOverAnswer;
        do {
            outputView.printStartMessage();
            outputView.printProducts(store);

            Map<PurchaseItem, PromotionNoticeResult> promotionNoticeResults = getPromotionNoticeResults(store);
            for (Entry<PurchaseItem, PromotionNoticeResult> entry : promotionNoticeResults.entrySet()) {
                PurchaseItem purchaseItem = entry.getKey();
                PromotionNoticeResult promotionNoticeResult = entry.getValue();
                if (promotionNoticeResult.promotionNoticeType() == MORE_QUANTITY) {
                    Answer answer = inputExtraPromotionNoticeResult(promotionNoticeResult);
                    if (answer == YES) {
                        purchaseItem.increaseQuantity(promotionNoticeResult.productQuantity());
                    }
                }
                if (promotionNoticeResult.promotionNoticeType() == NOT_APPLIED_QUANTITY) {
                    Answer answer = inputExtraPromotionNoticeResult(promotionNoticeResult);
                    if (answer == NO) {
                        purchaseItem.reduceQuantity(promotionNoticeResult.productQuantity());
                    }
                }
            }

            Answer membershipDiscountAnswer = inputMembershipDiscountRequest();
            List<PurchaseItem> purchaseItems = promotionNoticeResults.keySet().stream().toList();
            Receipt receipt = store.purchaseProducts(purchaseItems, membershipDiscountAnswer.toBoolean(),
                    DateTimes.now().toLocalDate());
            outputView.printReceipt(receipt);

            gameOverAnswer = inputMorePurchaseProducts();
        } while (gameOverAnswer.toBoolean());
    }

    private Answer inputExtraPromotionNoticeResult(PromotionNoticeResult promotionNoticeResult) {
        while (true) {
            try {
                String result = inputView.inputExtraPromotionNoticeRequest(promotionNoticeResult);
                StringToAnswerConverter stringToAnswerConverter = new StringToAnswerConverter();
                return stringToAnswerConverter.convert(result);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private Answer inputMorePurchaseProducts() {
        while (true) {
            try {
                String result = inputView.inputMorePurchaseProducts();
                StringToAnswerConverter stringToAnswerConverter = new StringToAnswerConverter();
                return stringToAnswerConverter.convert(result);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private Answer inputMembershipDiscountRequest() {
        while (true) {
            try {
                String result = inputView.inputMembershipDiscountRequest();
                StringToAnswerConverter stringToAnswerConverter = new StringToAnswerConverter();
                return stringToAnswerConverter.convert(result);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private Store initStore() {
        PromotionsInitializer promotionsInitializer = new PromotionsInitializer();
        Map<String, Promotion> promotions = promotionsInitializer.initialize();
        ProductsInitializer productsInitializer = new ProductsInitializer();
        return productsInitializer.initialize(promotions);
    }

    public Map<PurchaseItem, PromotionNoticeResult> getPromotionNoticeResults(Store store) {
        while (true) {
            try {
                String[] items = inputView.inputPurchaseItems().split(",");
                validateLength(items);
                StringToPurchaseItemConverter converter = new StringToPurchaseItemConverter();
                List<PurchaseItem> purchaseItems = Arrays.stream(items)
                        .map(String::trim)
                        .map(converter::convert)
                        .toList();
                return store.calculatePromotionNoticeResults(purchaseItems, DateTimes.now().toLocalDate());
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private void validateLength(String[] items) {
        if (items.length == 0) {
            throw new IllegalArgumentException("값을 입력하세요");
        }
    }
}
