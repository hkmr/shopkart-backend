package io.shopkart.shopkart.catalog.specification;

import io.shopkart.shopkart.catalog.Entity.Product;
import io.shopkart.shopkart.catalog.dto.ProductListRequestDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> filter(ProductListRequestDto request) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.query() != null && !request.query().isBlank()) {

                String search = "%" + request.query().toLowerCase() + "%";

                predicates.add(
                        cb.or(
                                cb.like(cb.lower(root.get("name")), search),
                                cb.like(cb.lower(root.get("sku")), search),
                                cb.like(cb.lower(root.get("description")), search)
                        )
                );

            }

            // Category filter
            if (request.categoryId() != null) {
                predicates.add(
                        cb.equal(
                                root.get("category").get("id"),
                                request.categoryId()
                        )
                );
            }

            // Brand filter
            if (request.brandId() != null) {
                predicates.add(
                        cb.equal(
                                root.get("brand").get("id"),
                                request.brandId()
                        )
                );
            }

            // Minimum price
            if (request.minPrice() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("price"),
                                request.minPrice()
                        )
                );
            }

            // Maximum price
            if (request.maxPrice() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("price"),
                                request.maxPrice()
                        )
                );
            }

            // Minimum rating
            if (request.rating() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("rating"),
                                request.rating()
                        )
                );
            }

            // Only active products
            predicates.add(
                    cb.isTrue(root.get("active"))
            );

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
