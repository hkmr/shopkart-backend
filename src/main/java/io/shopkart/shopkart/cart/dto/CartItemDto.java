package io.shopkart.shopkart.cart.dto;

import io.shopkart.shopkart.cart.entity.CartItem;
import io.shopkart.shopkart.catalog.Entity.Product;
import io.shopkart.shopkart.catalog.dto.ProductResponseDto;

import java.math.BigDecimal;

public record CartItemDto(
        ProductResponseDto product,
        Integer quantity
) {

    public static CartItemDto from(CartItem item) {
        Product product = item.getProduct();

        return new CartItemDto(
                ProductResponseDto.from(product),
                item.getQuantity()
        );
    }

}
