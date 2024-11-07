package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.exception.NotEnoughQuantityException;

class StoreTest {

    @Test
    void 존재하지_않는_상품인_경우_예외가_발생한다() {
        //given
        Map<String, Product> products = Map.of(
                "콜라", new Product("콜라", 1000, 10)
        );
        Store store = new Store(products, new HashMap<>());

        //when //then
        assertThatThrownBy(() -> store.validatePurchase("사이다", 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }

    @Test
    void 구매_수량이_재고_수량을_초과한_경우_예외가_발생한다() {
        //given
        Product product = new Product("콜라", 1000, 10);
        Promotion promotion = new Promotion("2+1", 2, 1, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        ProductPromotion productPromotion = new ProductPromotion(product, promotion, 3);

        Store store = new Store(Map.of("콜라", product), Map.of(product, productPromotion));

        //when //then
        assertThatThrownBy(() -> store.validatePurchase("콜라", 14))
                .isInstanceOf(NotEnoughQuantityException.class);
    }

    @Test
    void 구매_수량이_재고_수량을_초과하지_않은_경우_예외가_발생하지_않는다() {
        //given
        Product product = new Product("콜라", 1000, 10);
        Promotion promotion = new Promotion("2+1", 2, 1, LocalDate.now(), LocalDate.now());
        ProductPromotion productPromotion = new ProductPromotion(product, promotion, 3);

        Store store = new Store(Map.of("콜라", product), Map.of(product, productPromotion));

        //when //then
        assertThatNoException()
                .isThrownBy(() -> store.validatePurchase("콜라", 13));
    }

    @ParameterizedTest
    @CsvSource({
            "2, 1, 10, 0, 4, NOT_APPLIED_QUANTITY, 4",
            "2, 1, 10, 10, 1, NOT_APPLIED_QUANTITY, 1",
            "2, 1, 10, 7, 10, NOT_APPLIED_QUANTITY, 4",
            "2, 1, 10, 7, 7, NOT_APPLIED_QUANTITY, 1",
            "2, 1, 10, 10, 4, NOT_APPLIED_QUANTITY, 1",
            "2, 1, 10, 5, 6, NOT_APPLIED_QUANTITY, 3",
            "2, 1, 10, 10, 3, EXACT_PROMOTION, 3",
            "2, 1, 10, 10, 6, EXACT_PROMOTION, 6",
            "2, 1, 10, 10, 9, EXACT_PROMOTION, 9",
            "2, 1, 10, 10, 2, MORE_QUANTITY, 1",
            "2, 1, 10, 10, 5, MORE_QUANTITY, 1",
            "2, 1, 10, 10, 8, MORE_QUANTITY, 1",
            "1, 1, 3, 3, 4, NOT_APPLIED_QUANTITY, 2",
            "1, 1, 10, 0, 3, NOT_APPLIED_QUANTITY, 3",
            "1, 1, 10, 5, 5, NOT_APPLIED_QUANTITY, 1",
            "1, 1, 10, 10, 20, NOT_APPLIED_QUANTITY, 10",
            "1, 1, 10, 10, 2, EXACT_PROMOTION, 2",
            "1, 1, 10, 10, 10, EXACT_PROMOTION, 10",
            "1, 1, 4, 5, 4, EXACT_PROMOTION, 4",
            "1, 1, 10, 10, 1, MORE_QUANTITY, 1",
            "1, 1, 10, 10, 3, MORE_QUANTITY, 1",
            "1, 1, 10, 10, 5, MORE_QUANTITY, 1",
    })
    void 특정_상품을_구매하기_원할때_해당_상품의_프로모션에_따라_프로모션_안내_결과를_계산한다(
            int buyCount,
            int getCount,
            int productQuantity,
            int promotionQuantity,
            int purchaseQuantity,
            PromotionNoticeType promotionNoticeType,
            int resultProductQuantity) {
        //given
        Product product = new Product("콜라", 1000, productQuantity);
        Promotion promotion = create_Buy_N_Free_Count_Promotion(buyCount, getCount);
        ProductPromotion productPromotion = new ProductPromotion(product, promotion, promotionQuantity);
        Store store = new Store(Map.of("콜라", product), Map.of(product, productPromotion));

        //when
        PromotionNoticeResult result = store.calculatePromotionNoticeResult("콜라", purchaseQuantity);

        //then
        assertThat(result).extracting("type", "productName", "productQuantity")
                .containsExactly(
                        promotionNoticeType,
                        "콜라",
                        resultProductQuantity
                );
    }

    private Promotion create_Buy_N_Free_Count_Promotion(int buyCount, int getCount) {
        LocalDate currDate = DateTimes.now().toLocalDate();
        LocalDate startDate = currDate.minusDays(1);
        LocalDate endDate = currDate.plusDays(1);
        String promotionName = String.format("%d개 구매 시 %d개 무료 증정", buyCount, getCount);
        return new Promotion(promotionName, buyCount, getCount, startDate, endDate);
    }

}