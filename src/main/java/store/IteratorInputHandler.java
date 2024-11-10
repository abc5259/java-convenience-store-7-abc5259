package store;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.converter.StringToAnswerConverter;
import store.converter.StringToPurchaseItemConverter;
import store.domain.Answer;
import store.domain.PromotionNoticeResult;
import store.domain.PurchaseItem;
import store.domain.Store;
import store.view.InputView;

public class IteratorInputHandler {

    private final InputView inputView;
    private final IteratorInputTemplate iteratorInputTemplate;

    public IteratorInputHandler(InputView inputView,
                                IteratorInputTemplate iteratorInputTemplate) {
        this.inputView = inputView;
        this.iteratorInputTemplate = iteratorInputTemplate;
    }

    public Map<PurchaseItem, PromotionNoticeResult> calculatePromotionNoticeResults(Store store) {
        return iteratorInputTemplate.execute(
                inputView::inputPurchaseItems,
                inputValue -> {
                    String[] items = inputValue.split(",");
                    validateLength(items);
                    StringToPurchaseItemConverter converter = new StringToPurchaseItemConverter();
                    List<PurchaseItem> purchaseItems = createPurchaseItems(items, converter);
                    return store.calculatePromotionNoticeResults(purchaseItems, DateTimes.now().toLocalDate());
                }
        );
    }

    public Answer inputMembershipDiscountRequest() {
        return iteratorInputTemplate.execute(
                inputView::inputMembershipDiscountRequest,
                inputValue -> {
                    StringToAnswerConverter stringToAnswerConverter = new StringToAnswerConverter();
                    return stringToAnswerConverter.convert(inputValue);
                }
        );
    }

    public Answer inputExtraPromotionNoticeResult(PromotionNoticeResult promotionNoticeResult) {
        return iteratorInputTemplate.execute(
                () -> inputView.inputExtraPromotionNoticeRequest(promotionNoticeResult),
                inputValue -> {
                    StringToAnswerConverter stringToAnswerConverter = new StringToAnswerConverter();
                    return stringToAnswerConverter.convert(inputValue);
                }
        );
    }

    public Answer inputMorePurchaseProducts() {
        return iteratorInputTemplate.execute(
                inputView::inputMorePurchaseProducts,
                inputValue -> {
                    StringToAnswerConverter stringToAnswerConverter = new StringToAnswerConverter();
                    return stringToAnswerConverter.convert(inputValue);
                }
        );
    }

    private List<PurchaseItem> createPurchaseItems(String[] items, StringToPurchaseItemConverter converter) {
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        for (String item : items) {
            PurchaseItem newPurchaseItem = converter.convert(item.trim());

            purchaseItems.stream()
                    .filter(purchaseItem -> purchaseItem.isSameName(newPurchaseItem))
                    .findFirst()
                    .ifPresentOrElse(
                            purchaseItem -> purchaseItem.increaseQuantity(newPurchaseItem),
                            () -> purchaseItems.add(newPurchaseItem)
                    );
        }
        return purchaseItems;
    }

    private void validateLength(String[] items) {
        if (items.length == 0) {
            throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다.");
        }
    }
}
