package io.shopkart.shopkart.catalog.repository;

import io.shopkart.shopkart.catalog.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
