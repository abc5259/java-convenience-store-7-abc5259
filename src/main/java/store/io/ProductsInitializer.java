package store.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Promotion;

public class ProductsInitializer {

    private static final Path PRODUCT_PATH = Path.of("src/main/resources/products.md");

    public List<Product> initialize(Map<String, Promotion> promotions) throws IOException {
        List<String> productInfoLines = readAllProductInfoLines();
        List<Product> products = productInfoLines.stream()
                .map(line -> line.split(","))
                .map(productInfo -> new Product(
                        productInfo[0],
                        Integer.parseInt(productInfo[1]),
                        Integer.parseInt(productInfo[2]),
                        promotions.get(productInfo[3])))
                .toList();
        return products;
    }

    private List<String> readAllProductInfoLines() throws IOException {
        List<String> lines = Files.readAllLines(PRODUCT_PATH, StandardCharsets.UTF_8);
        lines.removeFirst(); // 첫 컬럼명 제거
        return lines;
    }
}
