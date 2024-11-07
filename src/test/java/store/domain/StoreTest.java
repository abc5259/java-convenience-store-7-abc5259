package store.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

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
        Promotion promotion = new Promotion("2+1", 2, 1, LocalDate.now(), LocalDate.now());
        ProductPromotion productPromotion = new ProductPromotion(product, promotion, 3);

        Store store = new Store(Map.of("콜라", product), Map.of(product, productPromotion));

        //when //then
        assertThatThrownBy(() -> store.validatePurchase("콜라", 13))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족합니다.");
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
}