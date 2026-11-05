package io.shopkart.shopkart.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;

public record AddCategoryRequestDto(
        @NotBlank @Size(min = 2, max = 100) String name,
        @NotBlank @Size(min= 10, max = 1000) String description,
        Long parentId,
        boolean active
) {
}
