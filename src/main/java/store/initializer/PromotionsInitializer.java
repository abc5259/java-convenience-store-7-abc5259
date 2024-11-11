package store.initializer;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.converter.StringToPromotionConverter;
import store.domain.Promotion;
import store.io.FileReader;

public class PromotionsInitializer {

    private static final Path PRODUCT_PATH = Path.of("src/main/resources/promotions.md");

    private final FileReader fileReader;
    private final StringToPromotionConverter stringToPromotionConverter;

    public PromotionsInitializer(FileReader fileReader, StringToPromotionConverter stringToPromotionConverter) {
        this.fileReader = fileReader;
        this.stringToPromotionConverter = stringToPromotionConverter;
    }

    public Map<String, Promotion> initialize() {
        List<String> promotionInfoLines = fileReader.readAllLines(PRODUCT_PATH);

        return promotionInfoLines.stream()
                .map(stringToPromotionConverter::convert)
                .collect(Collectors.toMap(
                        Promotion::getName,
                        promotion -> promotion
                ));
    }
}
