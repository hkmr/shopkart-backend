package io.shopkart.shopkart.catalog.dto;

import io.shopkart.shopkart.catalog.Entity.Product;

import java.util.List;

public record ProductListResponseDto(
        ProductListRequestDto params,
        List<ProductResponseDto> products
) {

    public static ProductListResponseDto from(ProductListRequestDto params, List<Product> products) {
        List<ProductResponseDto> productResponseDtoList = products
                .stream().map(product -> ProductResponseDto.from(product)).toList();

        return new ProductListResponseDto(params, productResponseDtoList);
    }

}
