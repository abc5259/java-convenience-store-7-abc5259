package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import store.exception.NonExistProductException;

class ProductReaderTest {

    @Test
    void 이름에_해당하는_상품을_찾는다() {
        //given
        Product product = new Product("콜라", 1000);
        ProductReader productReader = new ProductReader(Map.of(
                "콜라", product
        ));

        //when
        Product findProduct = productReader.findProductOrElseThrow("콜라");

        //then
        assertThat(findProduct).isEqualTo(product);
    }

    @Test
    void 이름에_해당하는_상품이_없다면_예외가_발생한다() {
        //given
        Product product = new Product("콜라", 1000);
        ProductReader productReader = new ProductReader(Map.of(
                "콜라", product
        ));

        //when //then
        assertThatThrownBy(() -> productReader.findProductOrElseThrow("에너지바"))
                .isInstanceOf(NonExistProductException.class);
    }

    @Test
    void 가지고_있는_모든_상품을_반환한다() {
        //given
        Product product1 = new Product("콜라", 1000);
        Product product2 = new Product("에너지바", 2000);
        ProductReader productReader = new ProductReader(Map.of(
                "콜라", product1,
                "에너지바", product2
        ));

        //when
        List<Product> allProducts = productReader.findAllProducts();

        //then
        assertThat(allProducts).isEqualTo(List.of(product1, product2));
    }
}