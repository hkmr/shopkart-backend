package io.shopkart.shopkart.cart.entity;

import io.shopkart.shopkart.catalog.Entity.Product;
import io.shopkart.shopkart.common.entity.BaseEntity;
import io.shopkart.shopkart.user.Entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    @Setter(lombok.AccessLevel.NONE)
    private List<CartItem> items = new ArrayList<>();

    public Cart(User user) {
        this.user = user;
    }

    // ---------------- lookups ----------------

    public Optional<CartItem> findItemById(Long itemId) {
        return items.stream().filter(i -> itemId.equals(i.getId())).findFirst();
    }

    public Optional<CartItem> findItemByProductId(Long productId) {
        return items.stream().filter(i -> productId.equals(i.getProduct().getId())).findFirst();
    }

    // ---------------- changes ----------------

    /**
     * Adds a new line, or increases the quantity when the product is already in the cart.
     * The price snapshot is refreshed to the product's current price.
     * Stock and limit checks belong to CartService, not here.
     */
    public CartItem addOrIncrease(Product product, int quantity) {
        CartItem item = findItemByProductId(product.getId()).orElse(null);
        if (item == null) {
            item = new CartItem(this, product, quantity, product.getPrice());
            items.add(item);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
            item.setUnitPrice(product.getPrice());
        }
        return item;
    }

    public void removeItem(Product product) {
        CartItem item = findItemByProductId(product.getId()).orElse(null);
        items.remove(item);                    // orphanRemoval deletes the row
    }

    public void clear() {
        items.clear();
    }

    // ---------------- calculations ----------------

    /** Sum of quantities of all lines (the number on the cart icon). */
    public int totalQuantity() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

}
