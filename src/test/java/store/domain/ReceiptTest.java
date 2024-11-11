package store.domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.domain.discount.AnswerDiscountCondition;
import store.domain.discount.MembershipDiscountPolicy;

class ReceiptTest {

    @Test
    void 총_구매액을_계산할_수_있다() {
        //given
        List<ProductPurchaseLog> productPurchaseLogs = List.of(
                new ProductPurchaseLog(new ProductInfo("콜라", 1000), 3, 1, 3),
                new ProductPurchaseLog(new ProductInfo("사이다", 1500), 2, 1, 2),
                new ProductPurchaseLog(new ProductInfo("에너지바", 1300), 0, 0, 4)
        );
        MembershipDiscountPolicy membershipDiscountPolicy = new MembershipDiscountPolicy(
                new AnswerDiscountCondition(Answer.YES));
        Receipt receipt = new Receipt(productPurchaseLogs, membershipDiscountPolicy);

        //when
        int totalPrice = receipt.getTotalPrice();

        //then
        Assertions.assertThat(totalPrice).isEqualTo(11200);
    }

    @Test
    void 행사_할인_금액을_계산할_수_있다() {
        //given
        List<ProductPurchaseLog> productPurchaseLogs = List.of(
                new ProductPurchaseLog(new ProductInfo("콜라", 1000), 3, 1, 3),
                new ProductPurchaseLog(new ProductInfo("사이다", 1500), 2, 1, 2),
                new ProductPurchaseLog(new ProductInfo("에너지바", 1300), 3, 1, 4)
        );
        MembershipDiscountPolicy membershipDiscountPolicy = new MembershipDiscountPolicy(
                new AnswerDiscountCondition(Answer.YES));
        Receipt receipt = new Receipt(productPurchaseLogs, membershipDiscountPolicy);

        //when
        int price = receipt.getTotalGiveawayProductPrice();

        //then
        Assertions.assertThat(price).isEqualTo(3800);
    }

    @ParameterizedTest()
    @CsvSource({"YES, 2610", "NO, 0"})
    void 멤버십_할인_금액을_계산할_수_있다(Answer membershipDiscountAnswer, int discountPrice) {
        //given
        List<ProductPurchaseLog> productPurchaseLogs = List.of(
                new ProductPurchaseLog(new ProductInfo("콜라", 1000), 3, 1, 5),
                new ProductPurchaseLog(new ProductInfo("사이다", 1500), 2, 1, 3),
                new ProductPurchaseLog(new ProductInfo("에너지바", 1300), 0, 0, 4)
        );
        MembershipDiscountPolicy membershipDiscountPolicy = new MembershipDiscountPolicy(
                new AnswerDiscountCondition(membershipDiscountAnswer));
        Receipt receipt = new Receipt(productPurchaseLogs, membershipDiscountPolicy);

        //when
        int price = receipt.getMembershipDiscountPrice();

        //then
        Assertions.assertThat(price).isEqualTo(discountPrice);
    }

    @Test
    void 멤버십_할인_금액은_8000원을_넘을_수_없다() {
        //given
        List<ProductPurchaseLog> productPurchaseLogs = List.of(
                new ProductPurchaseLog(new ProductInfo("에너지바", 1300), 0, 0, 100)
        );
        MembershipDiscountPolicy membershipDiscountPolicy = new MembershipDiscountPolicy(
                new AnswerDiscountCondition(Answer.YES));
        Receipt receipt = new Receipt(productPurchaseLogs, membershipDiscountPolicy);

        //when
        int price = receipt.getMembershipDiscountPrice();

        //then
        Assertions.assertThat(price).isEqualTo(8000);
    }

    @ParameterizedTest
    @CsvSource({"YES, 7140", "NO, 8700"})
    void 최종_금액을_계산할_수_있다(Answer membershipDiscountAnswer, int expectedPrice) {
        //given
        List<ProductPurchaseLog> productPurchaseLogs = List.of(
                new ProductPurchaseLog(new ProductInfo("콜라", 1000), 3, 1, 3),
                new ProductPurchaseLog(new ProductInfo("사이다", 1500), 2, 1, 2),
                new ProductPurchaseLog(new ProductInfo("에너지바", 1300), 0, 0, 4)
        );
        MembershipDiscountPolicy membershipDiscountPolicy = new MembershipDiscountPolicy(
                new AnswerDiscountCondition(membershipDiscountAnswer));
        Receipt receipt = new Receipt(productPurchaseLogs, membershipDiscountPolicy);

        //when
        int price = receipt.getLastPrice();

        //then
        Assertions.assertThat(price).isEqualTo(expectedPrice);
    }

    @Test
    void 적용되지_않는_프로모션_상품_가격을_계산한다() {
        //given
        List<ProductPurchaseLog> productPurchaseLogs = List.of(
                new ProductPurchaseLog(new ProductInfo("콜라", 1000), 3, 1, 5),
                new ProductPurchaseLog(new ProductInfo("사이다", 1500), 2, 1, 3),
                new ProductPurchaseLog(new ProductInfo("에너지바", 1300), 0, 0, 4)
        );
        MembershipDiscountPolicy membershipDiscountPolicy = new MembershipDiscountPolicy(
                new AnswerDiscountCondition(Answer.YES));
        Receipt receipt = new Receipt(productPurchaseLogs, membershipDiscountPolicy);

        //when
        int price = receipt.getTotalNotApplicablePromotionProductPrice();

        //then
        Assertions.assertThat(price).isEqualTo(8700);
    }

}