package io.shopkart.shopkart.user.dto;

import io.shopkart.shopkart.user.Entity.User;

import java.util.List;

public record AddressListResponseDto(
        List<AddressResponseDto> addressList
) {

    public static AddressListResponseDto from(User user) {
        return new AddressListResponseDto(
                user.getAddresses().stream().map(a -> AddressResponseDto.from(a)).toList()
        );
    }

}
