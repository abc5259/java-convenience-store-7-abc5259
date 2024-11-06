package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class StoreTest {

    @Test
    void 여러_상품을_한번에_등록할_수_있다() {
        //given
        List<Product> products = createProducts();

        //when
        Store store = new Store(products);

        //then
        assertThat(store).extracting("products")
                .isEqualTo(products);
    }

    @Test
    void 상품을_구매할_수_있다() {
        //given
        List<Product> products = createProducts();
        Store store = new Store(products);
        OrderItems orderItems = new OrderItems(
                List.of(new OrderItem("콜라", 1), new OrderItem("오렌지주스", 3))
        );

        //when
        store.purchase(orderItems);

        //then

    }

    private List<Product> createProducts() {
        List<Product> products = List.of(
                new Product("콜라", 1000, 10, new Promotion()),
                new Product("오렌지주스", 1000, 9, null),
                new Product("비타민워터", 1000, 3, new Promotion())
        );
        return products;
    }
}