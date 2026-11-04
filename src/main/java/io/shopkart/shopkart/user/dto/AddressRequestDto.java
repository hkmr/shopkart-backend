package io.shopkart.shopkart.user.dto;

import io.shopkart.shopkart.common.utils.ValidationPatterns;
import io.shopkart.shopkart.user.enums.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressRequestDto(

        @NotBlank(message = "Full name is required")
        @Size(max = 100, message = "Full name must be at most 100 characters")
        String fullName,

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = ValidationPatterns.PHONE,
                message = "Phone number must contain 10 to 15 digits, optionally starting with +"
        )
        String phone,

        @NotBlank(message = "Address line 1 is required")
        @Size(max = 200, message = "Address line 1 must be at most 200 characters")
        String line1,

        @Size(max = 200, message = "Address line 2 must be at most 200 characters")
        String line2,

        @NotBlank(message = "City is required")
        @Size(max = 100, message = "City must be at most 100 characters")
        String city,

        @NotBlank(message = "State is required")
        @Size(max = 100, message = "State must be at most 100 characters")
        String state,

        @NotBlank(message = "Postal code is required")
        @Pattern(
                regexp = ValidationPatterns.POSTAL_CODE,
                message = "Postal code must be 3 to 12 letters, digits, spaces, or hyphens"
        )
        String postalCode,

        @NotBlank(message = "Country is required")
        @Size(max = 100, message = "Country must be at most 100 characters")
        String country,

        @NotNull(message = "Address type is required")
        AddressType addressType,

        boolean isDefault
) {
}
