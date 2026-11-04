package io.shopkart.shopkart.user.dto;

import io.shopkart.shopkart.user.Entity.Address;
import io.shopkart.shopkart.user.enums.AddressType;

public record AddressResponseDto (
        Long id,
        String fullName,
        String phone,
        String line1,
        String line2,
        String city,
        String state,
        String postalCode,
        String country,
        String addressType,
        boolean isDefault
) {

    public static AddressResponseDto from(Address address) {
        return new AddressResponseDto(
                address.getId(),
                address.getFullName(),
                address.getPhone(),
                address.getLine1(),
                address.getLine2(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry(),
                address.getAddressType().toString(),
                address.isDefaultAddress()
        );
    }

}
