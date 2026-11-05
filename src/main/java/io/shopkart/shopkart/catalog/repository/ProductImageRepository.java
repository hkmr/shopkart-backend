package io.shopkart.shopkart.catalog.repository;

import io.shopkart.shopkart.catalog.Entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {


}
