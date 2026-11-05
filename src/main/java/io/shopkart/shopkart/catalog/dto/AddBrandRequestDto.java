package io.shopkart.shopkart.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddBrandRequestDto(
        @NotBlank @Size(min = 3, max = 100) String name,
        String logoUrl
) {
}
