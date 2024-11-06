package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import org.junit.jupiter.api.Test;

class StoreTest {

    @Test
    void 여러_상품을_한번에_등록할_수_있다() {
        //given
        Map<Product, Integer> products = Map.of(
                new Product("콜라", 1000, new Promotion()), 10,
                new Product("오렌지주스", 1000, null), 9,
                new Product("비타민워터", 1000, new Promotion()), 3
        );

        //when
        Store store = new Store(products);

        //then
        assertThat(store).extracting("products")
                .isEqualTo(products);
    }

    @Test
    void 등록되는_상품의_재고는_음수가_될_수_없다() {
        //given
        Map<Product, Integer> products = Map.of(
                new Product("콜라", 1000, new Promotion()), 10,
                new Product("오렌지주스", 1000, null), -1,
                new Product("비타민워터", 1000, new Promotion()), 3
        );

        //when //then
        assertThatThrownBy(() -> new Store(products))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 재고는 음수가 될 수 없습니다.");
    }
}