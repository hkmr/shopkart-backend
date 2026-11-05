package io.shopkart.shopkart.catalog.Entity;

import io.shopkart.shopkart.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Product brand (for example "Samsung"). Table: brands.
 * Brand 1---* Product; the FK lives in products.brand_id, so Brand has no collection of products
 * (load them with a query, never with brand.getProducts()).
 */

@Getter
@Setter
@NoArgsConstructor

@Table(name = "brands")
@Entity
public class Brand extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "logo_url", length = 300)
    private String logoUrl;

    public Brand(String name) {
        this.name = name;
    }

}
