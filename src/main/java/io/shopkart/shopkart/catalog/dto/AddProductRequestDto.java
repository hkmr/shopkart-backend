package io.shopkart.shopkart.catalog.dto;

import java.math.BigDecimal;

public record AddProductRequestDto(
        String name,
        String sku,
        String description,
        BigDecimal price,
        BigDecimal mrp,
        Long categoryId,
        Long brandId,
        Boolean active
) {
}
