package store.converter;

import store.domain.PurchaseItem;
import store.exception.ErrorMessage;

public class StringToPurchaseItemConverter implements Converter<String, PurchaseItem> {
    @Override
    public PurchaseItem convert(String source) {
        if (!source.startsWith("[") || !source.endsWith("]")) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT_MESSAGE.getMessage());
        }

        String itemInfo = source.substring(1, source.length() - 1);
        String[] itemInfos = itemInfo.split("-");
        if (itemInfos.length != 2) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT_MESSAGE.getMessage());
        }

        return new PurchaseItem(itemInfos[0].trim(), toInt(itemInfos[1]));
    }

    private int toInt(String itemPrice) {
        try {
            return Integer.parseInt(itemPrice.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT_MESSAGE.getMessage());
        }
    }
}
