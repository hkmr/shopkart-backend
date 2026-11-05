package io.shopkart.shopkart.catalog.dto;

import java.util.List;

public record CategoryListResponseDto(
        List<CategoryResponseDto> categories
) {
}
