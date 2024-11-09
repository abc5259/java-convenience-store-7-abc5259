package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.exception.NotEnoughQuantityException;

class PromotionProductTest {

    private static final LocalDate APPLICABLE_PROMOTION_DATE = LocalDate.of(2024, 11, 4);
    private static final LocalDate NOT_APPLICABLE_PROMOTION_DATE = LocalDate.of(2024, 11, 1);

    @ParameterizedTest
    @MethodSource("createPurchaseProductArguments")
    void 상품을_구매한다(
            Promotion promotion,
            Product product,
            int promotionProductQuantity,
            PurchaseItem purchaseItem,
            LocalDate purchaseDate,
            ProductPurchaseLog expectedProductPurchaseLog,
            int remainderPromotionProductQuantity,
            int remainderProductQuantity) {
        //given
        PromotionProduct promotionProduct = new PromotionProduct(product, promotion, promotionProductQuantity);

        //when
        ProductPurchaseLog productPurchaseLog = promotionProduct.purchase(purchaseItem, purchaseDate);

        //then
        assertThat(productPurchaseLog).isEqualTo(expectedProductPurchaseLog);
        assertThat(promotionProduct.getQuantity()).isEqualTo(remainderPromotionProductQuantity);
        assertThat(product.getQuantity()).isEqualTo(remainderProductQuantity);
        assertThat(purchaseItem.getPurchaseQuantity()).isEqualTo(0);
    }

    @Test
    void 구매_수량이_재고_수량을_초과한_경우_예외가_발생한다() {
        //given
        Promotion promotion = create_Buy_N_Free_Count_Promotion(2, 1);
        Product product = new Product("콜라", 1000, 2);
        PromotionProduct promotionProduct = new PromotionProduct(product, promotion, 3);
        PurchaseItem purchaseItem = new PurchaseItem("콜라", 6);

        //when //then
        assertThatThrownBy(() -> promotionProduct.purchase(purchaseItem, APPLICABLE_PROMOTION_DATE))
                .isInstanceOf(NotEnoughQuantityException.class);
    }

    private static Stream<Arguments> createPurchaseProductArguments() {
        return Stream.of(
                Arguments.of(
                        create_Buy_N_Free_Count_Promotion(2, 1),
                        new Product("콜라", 1000, 10),
                        7, // 프로모션 상품 재고
                        new PurchaseItem("콜라", 10),
                        APPLICABLE_PROMOTION_DATE,
                        new ProductPurchaseLog(new ProductInfo("콜라", 1000), 6, 2, 10),
                        0, // 사고 남은 프로모션 상품 재고
                        7 // 사고 남은 일반 상품 재고
                ),
                Arguments.of(
                        create_Buy_N_Free_Count_Promotion(2, 1),
                        new Product("콜라", 1000, 8),
                        7, // 프로모션 상품 재고
                        new PurchaseItem("콜라", 10),
                        NOT_APPLICABLE_PROMOTION_DATE,
                        new ProductPurchaseLog(new ProductInfo("콜라", 1000), 0, 0, 10),
                        5, // 사고 남은 프로모션 상품 재고
                        0 // 사고 남은 일반 상품 재고
                ),
                Arguments.of(
                        create_Buy_N_Free_Count_Promotion(2, 1),
                        new Product("콜라", 1000, 10),
                        10, // 프로모션 상품 재고
                        new PurchaseItem("콜라", 3),
                        APPLICABLE_PROMOTION_DATE,
                        new ProductPurchaseLog(new ProductInfo("콜라", 1000), 3, 1, 3),
                        7, // 사고 남은 프로모션 상품 재고
                        10 // 사고 남은 일반 상품 재고
                ),
                Arguments.of(
                        create_Buy_N_Free_Count_Promotion(1, 1),
                        new Product("오렌지주스", 1000, 0),
                        9, // 프로모션 상품 재고
                        new PurchaseItem("오렌지주스", 2),
                        APPLICABLE_PROMOTION_DATE,
                        new ProductPurchaseLog(new ProductInfo("오렌지주스", 1000), 2, 1, 2),
                        7, // 사고 남은 프로모션 상품 재고
                        0 // 사고 남은 일반 상품 재고
                ),
                Arguments.of(
                        create_Buy_N_Free_Count_Promotion(1, 1),
                        new Product("오렌지주스", 1000, 0),
                        9, // 프로모션 상품 재고
                        new PurchaseItem("오렌지주스", 2),
                        NOT_APPLICABLE_PROMOTION_DATE,
                        new ProductPurchaseLog(new ProductInfo("오렌지주스", 1000), 0, 0, 2),
                        7, // 사고 남은 프로모션 상품 재고
                        0 // 사고 남은 일반 상품 재고
                ),
                Arguments.of(
                        create_Buy_N_Free_Count_Promotion(1, 1),
                        new Product("오렌지주스", 1000, 0),
                        9, // 프로모션 상품 재고
                        new PurchaseItem("오렌지주스", 1),
                        APPLICABLE_PROMOTION_DATE,
                        new ProductPurchaseLog(new ProductInfo("오렌지주스", 1000), 0, 0, 1),
                        8, // 사고 남은 프로모션 상품 재고
                        0 // 사고 남은 일반 상품 재고
                )
        );
    }

    private static Promotion create_Buy_N_Free_Count_Promotion(int buyCount, int getCount) {
        LocalDate startDate = LocalDate.of(2024, 11, 2);
        LocalDate endDate = LocalDate.of(2024, 11, 4);
        String promotionName = String.format("%d개 구매 시 %d개 무료 증정", buyCount, getCount);
        return new Promotion(promotionName, buyCount, getCount, startDate, endDate);
    }
}