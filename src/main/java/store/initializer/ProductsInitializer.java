package store.initializer;

import java.nio.file.Path;
import java.util.List;
import store.converter.StringToTempProductConverter;
import store.io.FileReader;

public class ProductsInitializer {

    private static final Path PRODUCT_PATH = Path.of("src/main/resources/products.md");

    private final FileReader fileReader;
    private final StringToTempProductConverter TempProductConverter;

    public ProductsInitializer(FileReader fileReader, StringToTempProductConverter TempProductConverter) {
        this.fileReader = fileReader;
        this.TempProductConverter = TempProductConverter;
    }

    public List<TempProduct> initialize() {
        List<String> productInfoLines = fileReader.readAllLines(PRODUCT_PATH);

        return productInfoLines.stream()
                .map(TempProductConverter::convert)
                .toList();
    }
}
