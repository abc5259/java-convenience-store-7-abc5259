package store.converter;

import store.domain.PurchaseItem;

public class StringToPurchaseItemConverter implements Converter<String, PurchaseItem> {
    @Override
    public PurchaseItem convert(String source) {
        if (!source.startsWith("[") || !source.endsWith("]")) {
            throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다.");
        }

        String itemInfo = source.substring(1, source.length() - 1);
        String[] itemInfos = itemInfo.split("-");
        if (itemInfos.length != 2) {
            throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다.");
        }

        return new PurchaseItem(itemInfos[0].trim(), toInt(itemInfos[1]));
    }

    private int toInt(String itemPrice) {
        try {
            return Integer.parseInt(itemPrice.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바르지 않은 형식으로 입력했습니다.");
        }
    }
}
