package store.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.domain.Promotion;

public class PromotionsInitializer {

    private static final Path PRODUCT_PATH = Path.of("src/main/resources/promotions.md");

    public Map<String, Promotion> initialize() throws IOException {
        List<String> promotionInfoLines = readAllPromotionInfoLines();
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

    private List<String> readAllPromotionInfoLines() throws IOException {
        List<String> lines = Files.readAllLines(PRODUCT_PATH, StandardCharsets.UTF_8);
        lines.removeFirst(); // 첫 컬럼명 제거
        return lines;
    }
}
