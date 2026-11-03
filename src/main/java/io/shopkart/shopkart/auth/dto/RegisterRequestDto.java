package io.shopkart.shopkart.auth.dto;

import io.shopkart.shopkart.common.utils.ValidationPatterns;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto (
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Email(message = "Invalid email address") String email,
        @NotBlank @Pattern(regexp = ValidationPatterns.PASSWORD, message = ValidationPatterns.PASSWORD_MESSAGE) String password,
        @NotBlank @Pattern(regexp = ValidationPatterns.PHONE, message = ValidationPatterns.PHONE_MESSAGE) String phone
) {
}
