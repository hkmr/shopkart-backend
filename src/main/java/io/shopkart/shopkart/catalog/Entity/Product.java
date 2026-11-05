package io.shopkart.shopkart.catalog.Entity;

import io.shopkart.shopkart.common.entity.BaseEntity;
import io.shopkart.shopkart.user.Entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


/**
 * Sellable item. Table: products.
 *
 * Relationships
 *   Product *---1 Category   (required)
 *   Product *---1 Brand      (optional)
 *   Product *---1 User       (optional, the seller)
 *   Product 1---* ProductImage   (owned: cascade + orphanRemoval)
 *   Product 1---1 Inventory      (owned by Inventory, see note below)
 *
 * Why there is no "inventory" field here: the inverse side of a one-to-one is always loaded
 * eagerly by Hibernate, which would add one extra query per product on every list page.
 * Load stock with InventoryRepository.findByProductId(...) when you need it.
 */

@Getter
@Setter
@NoArgsConstructor

@Table(name = "products")
@Entity
public class Product extends BaseEntity {

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "slug", nullable = false, unique = true, length = 220)
    private String slug;

    @Column(name = "sku", nullable = false, unique = true, length = 60)
    private String sku;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "mrp", precision = 12, scale = 2)
    private BigDecimal mrp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    private List<String> images;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand Brand;
//    private String inventory;

    @Column(name = "avg_rating", nullable = false, precision = 3, scale = 2)
    private BigDecimal avgRating = BigDecimal.ZERO;

    @Column(name = "review_count", nullable = false)
    private int reviewCount = 0;

    @Column(name = "active", nullable = false)
    private boolean active = true;

}
