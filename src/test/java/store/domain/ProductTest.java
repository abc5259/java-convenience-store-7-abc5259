package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @ParameterizedTest
    @CsvSource({"-1", "-100", "-20"})
    void 상품의_수량은_음수가_될_수_없다(int quantity) {
        //given
        String name = "콜라";
        int price = 1000;
        Promotion promotion = new Promotion();

        //when //then
        assertThatThrownBy(() -> new Product(name, price, quantity, promotion))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량은 음수가 될 수 없습니다.");
    }
}
