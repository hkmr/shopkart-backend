package io.shopkart.shopkart.user.dto;

import io.shopkart.shopkart.user.Entity.User;

import java.util.List;

public record UserProfileResponseDto(
        String name,
        String email,
        String phone,
        List<String> roles,
        boolean isEmailVerified,
        List<AddressResponseDto> addresses
) {

    public static UserProfileResponseDto from(User user) {

        List<AddressResponseDto> addressList = user.getAddresses()
                .stream().map(AddressResponseDto::from).toList();

        List<String> roles = user.getRoles()
                .stream().map(role -> role.getName().toString()).toList();

        return new UserProfileResponseDto(
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                roles,
                user.isEmailVerified(),
                addressList
        );
    }

}
