package io.shopkart.shopkart.catalog.dto;

import java.math.BigInteger;

public record ProductListRequestDto(
        String query,
        Long categoryId,
        Long brandId,
        BigInteger minPrice,
        BigInteger maxPrice,
        Integer rating,
        String sort
) {
}
