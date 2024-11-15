package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PromotionTest {

    @ParameterizedTest
    @CsvSource({
            "3, 1, 4, 3, 4",
            "3, 1, 2, 2, 0",
            "3, 1, 7, 5, 4",
            "3, 1, 8, 10, 8",
            "2, 1, 3, 3, 3",
            "2, 1, 3, 2, 3",
            "2, 1, 4, 4, 3",
            "2, 1, 7, 10, 6",
            "1, 1, 2, 2, 2",
            "1, 1, 3, 2, 2",
            "1, 1, 4, 2, 2",
            "1, 1, 2, 3, 2",
            "1, 1, 3, 1, 2",
    })
    void 구매_수량에서_혜택을_받을_수_있는_조정된_상품_수량을_계산한다(
            int buyCount,
            int getCount,
            int promotionProductQuantity,
            int purchaseQuantity,
            int adjustedPromotionProductQuantity) {
        //given
        Promotion promotion = create_Buy_N_Free_Count_Promotion(buyCount, getCount);

        //when
        int result = promotion.calculateAdjustedPromotionQuantity(
                promotionProductQuantity,
                purchaseQuantity,
                LocalDate.now());

        //then
        assertThat(result).isEqualTo(adjustedPromotionProductQuantity);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 1, 4, 3, 0",
            "3, 1, 2, 2, 0",
            "3, 1, 7, 5, 4",
            "3, 1, 8, 10, 8",
            "2, 1, 3, 3, 3",
            "2, 1, 3, 2, 0",
            "2, 1, 4, 4, 3",
            "2, 1, 7, 10, 6",
            "1, 1, 2, 2, 2",
            "1, 1, 3, 2, 2",
            "1, 1, 4, 2, 2",
            "1, 1, 2, 3, 2",
            "1, 1, 3, 1, 0",
    })
    void 구매_수량_중_몇개의_상품이_프로모션_혜택을_받을_수_있는지_계산한다(
            int buyCount,
            int getCount,
            int promotionProductQuantity,
            int purchaseQuantity,
            int applicablePromotionProductQuantity) {
        //given
        Promotion promotion = create_Buy_N_Free_Count_Promotion(buyCount, getCount);

        //when
        int result = promotion.calculateApplicablePromotionProductQuantity(
                promotionProductQuantity,
                purchaseQuantity,
                LocalDate.now());

        //then
        assertThat(result).isEqualTo(applicablePromotionProductQuantity);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 1, 4, 3, 0",
            "3, 1, 2, 2, 0",
            "3, 1, 7, 5, 1",
            "3, 1, 8, 10, 2",
            "2, 1, 3, 3, 1",
            "2, 1, 3, 2, 0",
            "2, 1, 4, 4, 1",
            "2, 1, 7, 10, 2",
            "1, 1, 2, 2, 1",
            "1, 1, 3, 2, 1",
            "1, 1, 4, 2, 1",
            "1, 1, 2, 3, 1",
            "1, 1, 3, 1, 0",
    })
    void 구매_수량_중_몇개의_상품이_증정되는지_계산한다(
            int buyCount,
            int getCount,
            int promotionProductQuantity,
            int purchaseQuantity,
            int giveawayProductQuantity) {
        //given
        Promotion promotion = create_Buy_N_Free_Count_Promotion(buyCount, getCount);

        //when
        int result = promotion.calculateGiveawayProductQuantity(
                promotionProductQuantity,
                purchaseQuantity,
                LocalDate.now());

        //then
        assertThat(result).isEqualTo(giveawayProductQuantity);
    }

    private Promotion create_Buy_N_Free_Count_Promotion(int buyCount, int getCount) {
        LocalDate currDate = DateTimes.now().toLocalDate();
        LocalDate startDate = currDate.minusDays(1);
        LocalDate endDate = currDate.plusDays(1);
        String promotionName = String.format("%d개 구매 시 %d개 무료 증정", buyCount, getCount);
        return new Promotion(promotionName, buyCount, getCount, startDate, endDate);
    }
}