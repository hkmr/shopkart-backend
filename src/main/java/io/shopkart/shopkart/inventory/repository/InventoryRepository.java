package io.shopkart.shopkart.inventory.repository;

import io.shopkart.shopkart.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
