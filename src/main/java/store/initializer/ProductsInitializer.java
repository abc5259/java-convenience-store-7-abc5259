package store.initializer;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.converter.StringToProductTempConverter;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.Store;
import store.io.FileReader;

public class ProductsInitializer {

    private static final Path PRODUCT_PATH = Path.of("src/main/resources/products.md");

    private final FileReader fileReader;
    private final StringToProductTempConverter productTempConverter;

    public ProductsInitializer(FileReader fileReader, StringToProductTempConverter productTempConverter) {
        this.fileReader = fileReader;
        this.productTempConverter = productTempConverter;
    }

    public Store initialize(Map<String, Promotion> promotions) {
        List<String> productInfoLines = fileReader.readAllLines(PRODUCT_PATH);

        Map<String, Product> products = new LinkedHashMap<>();
        Map<Product, PromotionProduct> PromotionProducts = new LinkedHashMap<>();
        for (String productInfoLine : productInfoLines) {
            ProductTemp productTemp = productTempConverter.convert(productInfoLine);

            Product product = products.getOrDefault(
                    productTemp.name(),
                    new Product(productTemp.name(), productTemp.price()));

            if (productTemp.isNotPromotion()) {
                product.increaseQuantity(productTemp.quantity());
                products.put(productTemp.name(), product);
                continue;
            }

            Promotion promotion = promotions.get(productTemp.promotionName());
            PromotionProducts.put(product, new PromotionProduct(product, promotion, productTemp.quantity()));
            products.put(productTemp.name(), product);
        }
        return new Store(products, PromotionProducts);
    }
}
