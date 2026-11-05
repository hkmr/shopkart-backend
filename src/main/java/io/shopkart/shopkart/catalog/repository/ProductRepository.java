package io.shopkart.shopkart.catalog.repository;

import io.shopkart.shopkart.catalog.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
