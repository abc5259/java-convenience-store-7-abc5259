package store.io;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.Store;

public class ProductsInitializer {

    private static final Path PRODUCT_PATH = Path.of("src/main/resources/products.md");

    private final FileReader fileReader;

    public ProductsInitializer(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public Store initialize(Map<String, Promotion> promotions) {
        List<String> productInfoLines = fileReader.readAllLines(PRODUCT_PATH);

        Map<String, Product> products = new LinkedHashMap<>();
        Map<Product, PromotionProduct> PromotionProducts = new LinkedHashMap<>();
        for (String productInfoLine : productInfoLines) {
            String[] productInfoParts = productInfoLine.split(",");
            String productName = productInfoParts[0];
            int price = Integer.parseInt(productInfoParts[1]);
            int quantity = Integer.parseInt(productInfoParts[2]);
            String promotionName = productInfoParts[3];

            Product product = products.getOrDefault(productName, new Product(productName, price));

            if (promotionName.equals("null")) {
                product.increaseQuantity(quantity);
                products.put(productName, product);
                continue;
            }

            Promotion promotion = promotions.get(promotionName);
            PromotionProducts.put(product, new PromotionProduct(product, promotion, quantity));
            products.put(productName, product);
        }
        return new Store(products, PromotionProducts);
    }
}
