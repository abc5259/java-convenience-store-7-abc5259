package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.exception.NotEnoughQuantityException;

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
        PurchaseItem purchaseItem = new PurchaseItem("콜라", purchaseQuantity);

        //when
        ProductPurchaseLog productPurchaseLog = product.purchase(purchaseItem);

        //then
        assertThat(productPurchaseLog).isEqualTo(
                new ProductPurchaseLog(new ProductInfo("콜라", 1000), 0, 0, purchaseQuantity));
        assertThat(product.getQuantity()).isEqualTo(remainderProductQuantity);
        assertThat(purchaseItem.getPurchaseQuantity()).isEqualTo(0);
    }

    @Test
    void 상품_구매시_현재_재고보다_많은양의_구매_재고라면_예외가_발생한다() {
        //given
        Product product = new Product("콜라", 1000, 3);
        PurchaseItem purchaseItem = new PurchaseItem("콜라", 4);

        //when //then
        assertThatThrownBy(() -> product.purchase(purchaseItem))
                .isInstanceOf(NotEnoughQuantityException.class);
    }

    @Test
    void 재고를_증가시킨다() {
        //given
        Product product = new Product("콜라", 1000, 10);

        //when
        product.increaseQuantity(3);

        //then
        assertThat(product.getQuantity()).isEqualTo(13);
    }

    @Test
    void 재고를_감소시킨다() {
        //given
        Product product = new Product("콜라", 1000, 10);
        PurchaseItem purchaseItem = new PurchaseItem("콜라", 3);

        //when
        product.reduceQuantity(purchaseItem);

        //then
        assertThat(product.getQuantity()).isEqualTo(7);
        assertThat(purchaseItem.getPurchaseQuantity()).isEqualTo(0);
    }

    @Test
    void 재고를_감소시킬때_0보다_작아질_수_없다() {
        //given
        Product product = new Product("콜라", 1000, 2);
        PurchaseItem purchaseItem = new PurchaseItem("콜라", 3);

        //when
        product.reduceQuantity(purchaseItem);

        //then
        assertThat(product.getQuantity()).isEqualTo(0);
        assertThat(purchaseItem.getPurchaseQuantity()).isEqualTo(1);
    }

    @Test
    void 구매_재고만큼_구매할수_있는지_검증한다() {
        //given
        Product product = new Product("콜라", 1000, 2);

        //when //then
        assertThatThrownBy(() -> product.validatePurchaseQuantity(3))
                .isInstanceOf(NotEnoughQuantityException.class);
    }
}