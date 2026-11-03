package io.shopkart.shopkart.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDto (
        @NotBlank @Email String email,
        @NotBlank String password
) {
}
