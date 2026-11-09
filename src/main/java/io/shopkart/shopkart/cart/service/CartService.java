package io.shopkart.shopkart.cart.service;

import io.shopkart.shopkart.cart.dto.AddItemToCartRequestDto;
import io.shopkart.shopkart.cart.dto.CartResponseDto;
import io.shopkart.shopkart.cart.entity.Cart;
import io.shopkart.shopkart.cart.repository.CartRepository;
import io.shopkart.shopkart.catalog.Entity.Product;
import io.shopkart.shopkart.catalog.repository.ProductRepository;
import io.shopkart.shopkart.common.exception.ResourceNotFoundException;
import io.shopkart.shopkart.common.exception.UserNotFoundException;
import io.shopkart.shopkart.user.Entity.User;
import io.shopkart.shopkart.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public CartResponseDto getCart(Authentication authentication) {
        Cart cart = getOrCreateCart(authentication);
        return CartResponseDto.from(cart);
    }

    @Transactional
    public CartResponseDto addItem(Authentication authentication, AddItemToCartRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + requestDto.productId()));

        Cart cart = getOrCreateCart(authentication);
        cart.addOrIncrease(product, requestDto.quantity());
        return CartResponseDto.from(cart);
    }

    public CartResponseDto updateQuantity(Authentication authentication, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(authentication);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        cart.removeItem(product);
        cart.addOrIncrease(product, quantity);
        cart = cartRepository.save(cart);
        return CartResponseDto.from(cart);
    }

    @Transactional
    public CartResponseDto removeItem(Authentication authentication, Long productId) {
        Cart cart = getOrCreateCart(authentication);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        cart.removeItem(product);
        cart = cartRepository.save(cart);
        return CartResponseDto.from(cart);
    }

    @Transactional
    public CartResponseDto clearCart(Authentication authentication) {
        Cart cart = getOrCreateCart(authentication);
        cart.clear();
        cart = cartRepository.save(cart);
        return CartResponseDto.from(cart);
    }

    @Transactional
    private Cart getOrCreateCart(Authentication authentication) {

        User user = getUser(authentication);

        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart cart = new Cart(user);
                    return cartRepository.save(cart);
                });
    }

    @Transactional
    private User getUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User Not found"));
    }

}
