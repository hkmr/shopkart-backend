package io.shopkart.shopkart.catalog.repository;

import io.shopkart.shopkart.catalog.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCategoryId(Long categoryId);
    List<Product> findAllByCategoryId(Long categoryId);

}
