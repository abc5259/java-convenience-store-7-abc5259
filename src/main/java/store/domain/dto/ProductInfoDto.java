package store.domain.dto;

public record ProductInfoDto(
        String name,
        int price,
        int quantity,
        String promotionName
) {
}
