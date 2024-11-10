package store.io;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.domain.Promotion;

public class PromotionsInitializer {

    private static final Path PRODUCT_PATH = Path.of("src/main/resources/promotions.md");
    private final FileReader fileReader;

    public PromotionsInitializer(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public Map<String, Promotion> initialize() {
        List<String> promotionInfoLines = fileReader.readAllLines(PRODUCT_PATH);
        return promotionInfoLines.stream()
                .map(line -> line.split(","))
                .map(promotionInfo -> new Promotion(
                        promotionInfo[0],
                        Integer.parseInt(promotionInfo[1]),
                        Integer.parseInt(promotionInfo[2]),
                        LocalDate.parse(promotionInfo[3]),
                        LocalDate.parse(promotionInfo[4])
                ))
                .collect(Collectors.toMap(
                        Promotion::getName,
                        promotion -> promotion
                ));
    }
}
