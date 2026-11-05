package io.shopkart.shopkart.catalog.dto;

import io.shopkart.shopkart.catalog.Entity.Product;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponseDto(
        String name,
        String slug,
        String sku,
        String description,
        BigDecimal price,
        BigDecimal mrp,
        String category,
        List<String> images,
        BigDecimal avgRating,
        Integer reviewCount,
        Boolean active
) {

    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(
                product.getName(),
                product.getSlug(),
                product.getSku(),
                product.getDescription(),
                product.getPrice(),
                product.getMrp(),
                product.getCategory().getName(),
                product.getImages(),
                product.getAvgRating(),
                product.getReviewCount(),
                product.isActive()
        );
    }

}
