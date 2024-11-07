package store.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.ProductPromotion;
import store.domain.Promotion;
import store.domain.Store;

public class ProductsInitializer {

    private static final Path PRODUCT_PATH = Path.of("src/main/resources/products.md");

    public Store initialize(Map<String, Promotion> promotions) {
        List<String> productInfoLines = readAllProductInfoLines();

        Map<String, Product> products = new LinkedHashMap<>();
        Map<Product, ProductPromotion> productPromotions = new LinkedHashMap<>();
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
            }
            if (!promotionName.equals("null")) {
                Promotion promotion = promotions.get(promotionName);
                ProductPromotion productPromotion = new ProductPromotion(product, promotion, quantity);
                productPromotions.put(product, productPromotion);
                products.put(productName, product);
            }
        }
        
        return new Store(products, productPromotions);
    }

    private List<String> readAllProductInfoLines() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(PRODUCT_PATH, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lines.removeFirst(); // 첫 컬럼명 제거
        return lines;
    }
}
