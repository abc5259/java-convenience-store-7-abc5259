package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.domain.PromotionNoticeResult;
import store.domain.PromotionNoticeType;

public class InputView {
    private static final String INPUT_PURCHASE_ITEMS_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String INPUT_EXTRA_PROMOTION_QUANTITY_FORMAT = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n";
    private static final String INPUT_NOT_APPLIED_PROMOTION_QUANTITY_FORMAT = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n";
    private static final String INPUT_MEMBERSHIP_DISCOUNT_ANSWER = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String INPUT_ADDITIONAL_PURCHASE_ANSWER = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public String inputPurchaseItems() {
        System.out.println(INPUT_PURCHASE_ITEMS_MESSAGE);
        return Console.readLine();
    }

    public String inputExtraPromotionNoticeRequest(PromotionNoticeResult promotionNoticeResult) {
        System.out.println();
        return inputExtraPromotionNoticeResponse(promotionNoticeResult);
    }

    public String inputMembershipDiscountRequest() {
        System.out.println();
        System.out.println(INPUT_MEMBERSHIP_DISCOUNT_ANSWER);
        return Console.readLine();
    }

    private String inputExtraPromotionNoticeResponse(PromotionNoticeResult promotionNoticeResult) {
        if (promotionNoticeResult.promotionNoticeType() == PromotionNoticeType.MORE_QUANTITY) {
            System.out.printf(INPUT_EXTRA_PROMOTION_QUANTITY_FORMAT,
                    promotionNoticeResult.productName(),
                    promotionNoticeResult.productQuantity());
            return Console.readLine();
        }

        if (promotionNoticeResult.promotionNoticeType() == PromotionNoticeType.NOT_APPLIED_QUANTITY) {
            System.out.printf(INPUT_NOT_APPLIED_PROMOTION_QUANTITY_FORMAT,
                    promotionNoticeResult.productName(),
                    promotionNoticeResult.productQuantity());
            return Console.readLine();
        }

        throw new IllegalArgumentException("추가 요청 사항이 없습니다.");
    }

    public String inputMorePurchaseProducts() {
        System.out.println(INPUT_ADDITIONAL_PURCHASE_ANSWER);
        String answer = Console.readLine();
        System.out.println();
        return answer;
    }
}
