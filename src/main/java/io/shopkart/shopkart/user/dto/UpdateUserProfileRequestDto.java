package io.shopkart.shopkart.user.dto;

public record UpdateUserProfileRequestDto(
        String name,
        String phone
) {
}
