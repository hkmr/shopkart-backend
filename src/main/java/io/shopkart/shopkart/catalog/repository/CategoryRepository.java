package io.shopkart.shopkart.catalog.repository;

import io.shopkart.shopkart.catalog.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByParentIsNull();
    boolean existsByParentId(Long parentId);
}
