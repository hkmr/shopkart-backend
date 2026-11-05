package io.shopkart.shopkart.catalog.Entity;

import io.shopkart.shopkart.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** One picture of a product. Table: product_images. Owned by Product (cascade + orphanRemoval). */

@Getter
@Setter
@NoArgsConstructor

@Table(name = "product_images")
@Entity
public class ProductImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "url", nullable = false, length = 400)
    private String url;

    @Column(name = "alt_text", length = 150)
    private String altText;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder = 0;

    @Column(name = "is_primary", nullable = false)
    private boolean primaryImage = false;

    public ProductImage(String url, String altText, int sortOrder, boolean primaryImage) {
        this.url = url;
        this.altText = altText;
        this.sortOrder = sortOrder;
        this.primaryImage = primaryImage;
    }

}
