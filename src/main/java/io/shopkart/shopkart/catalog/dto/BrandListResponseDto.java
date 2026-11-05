package io.shopkart.shopkart.catalog.dto;

import java.util.List;

public record BrandListResponseDto(
        List<BrandResponseDto> brands
) {
}
