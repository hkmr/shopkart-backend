package io.shopkart.shopkart.catalog.dto;

import io.shopkart.shopkart.catalog.Entity.Category;

import java.util.List;

public record CategoryResponseDto(
        Long id,
        String name,
        String slug,
        String description,
        Category parent,
        List<Category> children,
        boolean active
) {

    public static CategoryResponseDto from(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                category.getParent(),
                category.getChildren(),
                category.isActive()
        );
    }
}
