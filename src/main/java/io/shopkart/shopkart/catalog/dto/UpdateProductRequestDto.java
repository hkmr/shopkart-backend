package io.shopkart.shopkart.catalog.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateProductRequestDto(
        @NotBlank(message = "Product name is required")
        @Size(max = 150, message = "Product name cannot exceed 150 characters")
        String name,

        @NotBlank(message = "SKU is required")
        @Size(max = 50, message = "SKU cannot exceed 50 characters")
        String sku,

        @NotBlank(message = "Product description is required")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        @Digits(integer = 10, fraction = 2, message = "Invalid price format")
        BigDecimal price,

        @NotNull(message = "MRP is required")
        @DecimalMin(value = "0.01", message = "MRP must be greater than 0")
        @Digits(integer = 10, fraction = 2, message = "Invalid MRP format")
        BigDecimal mrp,

        @NotNull(message = "Category is required")
        @Positive(message = "Invalid category ID")
        Long categoryId,

        @NotNull(message = "Brand is required")
        @Positive(message = "Invalid brand ID")
        Long brandId,

        @NotNull(message = "Active status is required")
        Boolean active
) {
}
