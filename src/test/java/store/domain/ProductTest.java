package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void 상품은_이름_가격_수량_프로모션으로_이루어져_있다() {
        //given
        String name = "콜라";
        int price = 1000;
        int quantity = 10;
        Promotion promotion = new Promotion();

        //when
        Product product = new Product(name, price, 10, promotion);

        //then
        assertThat(product)
                .extracting("name", "price", "quantity", "promotion")
                .containsExactly(name, price, quantity, promotion);
    }
}
