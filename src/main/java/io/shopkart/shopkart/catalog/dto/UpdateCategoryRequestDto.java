package io.shopkart.shopkart.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequestDto(
        @NotBlank @Size(min = 2, max = 100) String name,
        @NotBlank @Size(min= 10, max = 1000) String description,
        Long parentId,
        boolean active
) {
}
