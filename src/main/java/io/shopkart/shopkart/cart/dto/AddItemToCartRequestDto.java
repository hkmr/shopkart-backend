package io.shopkart.shopkart.cart.dto;

public record AddItemToCartRequestDto(
        Long productId,
        Integer quantity
) {
}
