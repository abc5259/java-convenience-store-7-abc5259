package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.domain.PromotionNoticeResult;
import store.domain.PromotionNoticeType;

public class InputView {
    private static final String INPUT_PURCHASE_ITEMS_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1]";

    public String inputPurchaseItems() {
        System.out.println(INPUT_PURCHASE_ITEMS_MESSAGE);
        return Console.readLine();
    }

    public String inputExtraPromotionNoticeRequest(PromotionNoticeResult promotionNoticeResult) {
        System.out.println();
        if (promotionNoticeResult.promotionNoticeType() == PromotionNoticeType.MORE_QUANTITY) {
            System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n",
                    promotionNoticeResult.productName(),
                    promotionNoticeResult.productQuantity());
            return Console.readLine();
        }

        if (promotionNoticeResult.promotionNoticeType() == PromotionNoticeType.NOT_APPLIED_QUANTITY) {
            System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n",
                    promotionNoticeResult.productName(),
                    promotionNoticeResult.productQuantity());
            return Console.readLine();
        }

        throw new IllegalArgumentException("추가 요청 사항이 없습니다.");
    }
}
