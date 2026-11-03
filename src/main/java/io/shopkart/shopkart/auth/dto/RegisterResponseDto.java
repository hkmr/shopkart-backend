package io.shopkart.shopkart.auth.dto;

import io.shopkart.shopkart.user.Entity.User;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record RegisterResponseDto(
        Long id,
        String name,
        String email,
        String phone,
        boolean emailVerified,
        List<String> roles,
        String message,
        Instant createdAt
) {
    public static RegisterResponseDto from(User user, String message) {

        List<String> roles = user.getRoles().stream().map(role -> role.getName().toString()).toList();

        return new RegisterResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.isEmailVerified(),
                roles,
                message,
                Instant.now()
        );
    }
}
