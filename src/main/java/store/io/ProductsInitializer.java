package store.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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

    public Store initialize(Map<String, Promotion> promotions) {
        //TODO: 함수 10줄로 줄이기
        List<String> productInfoLines = readAllProductInfoLines();

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
            }
            if (!promotionName.equals("null")) {
                Promotion promotion = promotions.get(promotionName);
                PromotionProduct PromotionProduct = new PromotionProduct(product, promotion, quantity);
                PromotionProducts.put(product, PromotionProduct);
                products.put(productName, product);
            }
        }
        System.out.println(products);
        System.out.println();
        System.out.println(PromotionProducts);
        return new Store(products, PromotionProducts);
    }

    private List<String> readAllProductInfoLines() {
        try {
            List<String> lines = Files.readAllLines(PRODUCT_PATH, StandardCharsets.UTF_8);
            lines.removeFirst(); // 첫 컬럼명 제거
            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
