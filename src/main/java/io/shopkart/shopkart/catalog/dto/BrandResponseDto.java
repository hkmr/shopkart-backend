package io.shopkart.shopkart.catalog.dto;

import io.shopkart.shopkart.catalog.Entity.Brand;

public record BrandResponseDto(
        Long id,
        String name,
        String logoUrl
) {

    public static BrandResponseDto from(Brand brand) {
        return new BrandResponseDto(
                brand.getId(),
                brand.getName(),
                brand.getLogoUrl()
        );
    }

}
