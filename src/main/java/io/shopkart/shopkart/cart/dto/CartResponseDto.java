package io.shopkart.shopkart.cart.dto;

import io.shopkart.shopkart.cart.entity.Cart;

import java.util.List;

public record CartResponseDto(
    List<CartItemDto> cartItems
) {

    public static CartResponseDto from(Cart cart) {

        var items = cart.getItems()
                .stream().map(item -> CartItemDto.from(item)).toList();

        return new CartResponseDto(items);
    }

}
