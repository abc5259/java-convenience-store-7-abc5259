package store.converter;

import store.exception.ErrorMessage;
import store.initializer.ProductTemp;

public class StringToProductTempConverter implements Converter<String, ProductTemp> {
    @Override
    public ProductTemp convert(String source) {
        String[] productInfo = source.split(",");
        return new ProductTemp(
                productInfo[0].trim(),
                toInt(productInfo[1]),
                toInt(productInfo[2]),
                productInfo[3].trim()
        );
    }

    private int toInt(String source) {
        try {
            return Integer.parseInt(source.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_RESOURCE_FORMAT.getMessage(), e);
        }
    }

}
