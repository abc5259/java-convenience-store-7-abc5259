package store.initializer;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.converter.StringToTempProductConverter;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.Store;
import store.io.FileReader;

public class ProductsInitializer {

    private static final Path PRODUCT_PATH = Path.of("src/main/resources/products.md");

    private final FileReader fileReader;
    private final StringToTempProductConverter TempProductConverter;

    public ProductsInitializer(FileReader fileReader, StringToTempProductConverter TempProductConverter) {
        this.fileReader = fileReader;
        this.TempProductConverter = TempProductConverter;
    }

    public Store initialize(Map<String, Promotion> promotions) {
        List<String> productInfoLines = fileReader.readAllLines(PRODUCT_PATH);

        Map<String, Product> products = new LinkedHashMap<>();
        Map<Product, PromotionProduct> PromotionProducts = new LinkedHashMap<>();
        for (String productInfoLine : productInfoLines) {
            TempProduct tempProduct = TempProductConverter.convert(productInfoLine);

            Product product = products.getOrDefault(
                    tempProduct.name(),
                    new Product(tempProduct.name(), tempProduct.price()));

            if (tempProduct.isNotPromotion()) {
                product.increaseQuantity(tempProduct.quantity());
                products.put(tempProduct.name(), product);
                continue;
            }

            Promotion promotion = promotions.get(tempProduct.promotionName());
            PromotionProducts.put(product, new PromotionProduct(product, promotion, tempProduct.quantity()));
            products.put(tempProduct.name(), product);
        }
        return new Store(products, PromotionProducts);
    }
}
