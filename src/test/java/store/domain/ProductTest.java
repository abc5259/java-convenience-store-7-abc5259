package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProductTest {

    @ParameterizedTest
    @CsvSource({
            "10, 9, 1",
            "8, 4, 4",
            "10, 10, 0",
    })
    void 상품을_구매한다(int productQuantity, int purchaseQuantity, int remainderProductQuantity) {
        //given
        Product product = new Product("콜라", 1000, productQuantity);

        //when
        ProductPurchaseLog productPurchaseLog = product.purchase(new PurchaseItem("콜라", purchaseQuantity));

        //then
        assertThat(productPurchaseLog).isEqualTo(new ProductPurchaseLog(0, 0, purchaseQuantity));
        assertThat(product.getQuantity()).isEqualTo(remainderProductQuantity);
    }
}