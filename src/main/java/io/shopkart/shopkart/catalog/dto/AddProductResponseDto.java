package io.shopkart.shopkart.catalog.dto;

import io.shopkart.shopkart.catalog.Entity.Brand;
import io.shopkart.shopkart.catalog.Entity.Category;
import io.shopkart.shopkart.catalog.Entity.Product;

import java.math.BigDecimal;

public record AddProductResponseDto(
        Long productId,
        String name,
        String sku,
        String description,
        BigDecimal price,
        BigDecimal mrp,
        Category category,
        Brand brand,
        BigDecimal avgRating,
        int reviewCount,
        Boolean active
) {

    public static AddProductResponseDto from(Product product) {
        return new AddProductResponseDto(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getDescription(),
                product.getPrice(),
                product.getMrp(),
                product.getCategory(),
                product.getBrand(),
                product.getAvgRating(),
                product.getReviewCount(),
                product.isActive()
        );
    }

}
