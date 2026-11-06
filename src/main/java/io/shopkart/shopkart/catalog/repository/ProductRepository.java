package io.shopkart.shopkart.catalog.repository;

import io.shopkart.shopkart.catalog.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    boolean existsByCategoryId(Long categoryId);
    List<Product> findAllByCategoryId(Long categoryId);
    Optional<Product> findBySlug(String slug);
}
