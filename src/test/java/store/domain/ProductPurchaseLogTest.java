package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProductPurchaseLogTest {

    @Test
    void 전체_가격을_계산한다() {
        //given
        ProductInfo productInfo = new ProductInfo("콜라", 1000);
        ProductPurchaseLog productPurchaseLog = new ProductPurchaseLog(
                productInfo,
                3, 1, 5);

        //when
        int totalPrice = productPurchaseLog.calculateTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo(5000);
    }

    @Test
    void 증정_상품_가격을_계산한다() {
        //given
        ProductInfo productInfo = new ProductInfo("콜라", 1000);
        ProductPurchaseLog productPurchaseLog = new ProductPurchaseLog(
                productInfo,
                6, 2, 6);

        //when
        int result = productPurchaseLog.calculateGiveawayProductPrice();

        //then
        assertThat(result).isEqualTo(2000);
    }

    @Test
    void 프로모션이_적용되지_않은_상품_가격을_계산한다() {
        //given
        ProductInfo productInfo = new ProductInfo("콜라", 1000);
        ProductPurchaseLog productPurchaseLog = new ProductPurchaseLog(
                productInfo,
                3, 1, 5);

        //when
        int result = productPurchaseLog.calculateNotApplicablePromotionProductPrice();

        //then
        assertThat(result).isEqualTo(2000);
    }
}