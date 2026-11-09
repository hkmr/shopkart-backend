package io.shopkart.shopkart.cart.controller;

import io.shopkart.shopkart.cart.dto.AddItemToCartRequestDto;
import io.shopkart.shopkart.cart.dto.CartResponseDto;
import io.shopkart.shopkart.cart.dto.UpdateCartItemRequestDto;
import io.shopkart.shopkart.cart.service.CartService;
import io.shopkart.shopkart.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> getCartDetail(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(cartService.getCart(authentication)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> addCartItem(
            Authentication authentication,
            @RequestBody AddItemToCartRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(cartService.addItem(authentication, requestDto)));
    }

    @PutMapping("/items")
    public ResponseEntity<ApiResponse<CartResponseDto>> updateCartItemQuantity(
            Authentication authentication,
            @RequestBody UpdateCartItemRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        cartService.updateQuantity(
                                authentication, requestDto.productId(), requestDto.quantity()
                        )));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<CartResponseDto>> removeCartItem(
            Authentication authentication,
            @PathVariable Long itemId
    ) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success(cartService.removeItem(authentication, itemId)));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> clearCartItems(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success(cartService.clearCart(authentication)));
    }

}
