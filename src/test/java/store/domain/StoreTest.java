package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class StoreTest {

    @Test
    void 여러_상품을_한번에_등록할_수_있다() {
        //given
        Map<Product, Integer> products = Map.of(
                new Product("콜라", 1000, new Promotion()), 10,
                new Product("콜라", 1000, null), 9,
                new Product("콜라", 1000, new Promotion()), 3
        );

        //when
        Store store = new Store(products);

        //then
        assertThat(store).extracting("products")
                .isEqualTo(products);
    }
}