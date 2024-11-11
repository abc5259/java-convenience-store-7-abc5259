package store.converter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import store.domain.Promotion;
import store.exception.ErrorMessage;

public class StringToPromotionConverter implements Converter<String, Promotion> {
    @Override
    public Promotion convert(String source) {
        String[] promotionInfo = source.split(",");
        return new Promotion(
                promotionInfo[0].trim(),
                toInt(promotionInfo[1]),
                toInt(promotionInfo[2]),
                toLocalDate(promotionInfo[3]),
                toLocalDate(promotionInfo[4]));
    }

    private int toInt(String source) {
        try {
            return Integer.parseInt(source.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_RESOURCE_FORMAT.getMessage(), e);
        }
    }

    private LocalDate toLocalDate(String source) {
        try {
            return LocalDate.parse(source.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_RESOURCE_FORMAT.getMessage(), e);
        }
    }
}
