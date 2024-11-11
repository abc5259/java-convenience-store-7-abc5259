package store.domain;

import static store.domain.PromotionNoticeType.EXACT_PROMOTION;
import static store.domain.PromotionNoticeType.MORE_QUANTITY;
import static store.domain.PromotionNoticeType.NOT_APPLIED_QUANTITY;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PromotionNoticeResultTest {

    @Test
    void 구매수량이_조정된_프로모션_수량과_같은_경우_상품_재고가_정확하다라는_알림을_준다() {
        //when
        PromotionNoticeResult promotionNoticeResult = PromotionNoticeResult.from("name", 3, 3);

        //then
        Assertions.assertThat(promotionNoticeResult).extracting("promotionNoticeType", "productQuantity")
                .containsExactly(EXACT_PROMOTION, 3);
    }

    @Test
    void 구매수량이_조정된_프로모션_수량보다_작은_경우_더_많은_재고를_사야한다는_알림을_준다() {
        //when
        PromotionNoticeResult promotionNoticeResult = PromotionNoticeResult.from("name", 3, 4);

        //then
        Assertions.assertThat(promotionNoticeResult).extracting("promotionNoticeType", "productQuantity")
                .containsExactly(MORE_QUANTITY, 1);
    }

    @Test
    void 구매수량이_조정된_프로모션_수량보다_큰_경우_프로모션이_적용되지_않는_수량을_알려준다() {
        //when
        PromotionNoticeResult promotionNoticeResult = PromotionNoticeResult.from("name", 5, 3);

        //then
        Assertions.assertThat(promotionNoticeResult).extracting("promotionNoticeType", "productQuantity")
                .containsExactly(NOT_APPLIED_QUANTITY, 2);
    }
}