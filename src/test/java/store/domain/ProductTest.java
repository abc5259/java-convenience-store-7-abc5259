package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void 상품은_이름_가격_프로모션으로_이루어져_있다() {
        //given
        String name = "콜라";
        int price = 1000;
        Promotion promotion = new Promotion();

        //when
        Product product = new Product(name, price, promotion);

        //then
        assertThat(product)
                .extracting("name", "price", "promotion")
                .containsExactly(name, price, promotion);
    }
}
