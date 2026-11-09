package io.shopkart.shopkart.cart.dto;

public record UpdateCartItemRequestDto(
        Long productId,
        Integer quantity
) {
}
