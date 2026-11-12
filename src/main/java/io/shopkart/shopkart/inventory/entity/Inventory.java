package io.shopkart.shopkart.inventory.entity;

import io.shopkart.shopkart.catalog.Entity.Product;
import io.shopkart.shopkart.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "entity")
public class Inventory extends BaseEntity {

    @OneToOne(mappedBy = "product_id")
    private Product product;

    private Integer quantity;

    private Integer reservedQuantity;

    private Integer stockAlertThreshold;

}
