package io.shopkart.shopkart.catalog.dto;

import io.shopkart.shopkart.catalog.Entity.Category;

import java.util.List;

public record AddCategoryResponseDto(
        Long id,
        String name,
        String slug,
        String description,
        Category parent,
        List<Category> children,
        boolean active
) {

    public static AddCategoryResponseDto from(Category category) {
        return new AddCategoryResponseDto(
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
