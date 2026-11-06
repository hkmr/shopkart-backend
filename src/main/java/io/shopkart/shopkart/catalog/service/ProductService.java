package io.shopkart.shopkart.catalog.service;

import io.shopkart.shopkart.catalog.Entity.Brand;
import io.shopkart.shopkart.catalog.Entity.Category;
import io.shopkart.shopkart.catalog.Entity.Product;
import io.shopkart.shopkart.catalog.dto.*;
import io.shopkart.shopkart.catalog.repository.BrandRepository;
import io.shopkart.shopkart.catalog.repository.CategoryRepository;
import io.shopkart.shopkart.catalog.repository.ProductRepository;
import io.shopkart.shopkart.catalog.specification.ProductSpecification;
import io.shopkart.shopkart.common.exception.ResourceNotFoundException;
import io.shopkart.shopkart.common.exception.UserNotFoundException;
import io.shopkart.shopkart.common.utils.SlugUtils;
import io.shopkart.shopkart.user.Entity.User;
import io.shopkart.shopkart.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class ProductService {

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private BrandRepository brandRepository;

    public ProductService(
            ProductRepository productRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            BrandRepository brandRepository
    ) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    public ProductListResponseDto getProductList(ProductListRequestDto requestDto) {

        Specification<Product> specification = ProductSpecification.filter(requestDto);
        Sort sort = getSort(requestDto.sort());

        List<Product> productList = productRepository.findAll(specification, sort);

        return ProductListResponseDto.from(requestDto, productList);
    }

    public ProductResponseDto getProductDetailById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        return ProductResponseDto.from(product);
    }

    public ProductResponseDto getProductDetailBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        return ProductResponseDto.from(product);
    }

    @Transactional
    public AddProductResponseDto addProduct(Authentication authentication, AddProductRequestDto requestDto) {

        User user = getUser(authentication);

        Category category = categoryRepository.findById(requestDto.categoryId())
                .orElseThrow(() ->new ResourceNotFoundException("Category Not found"));

        Brand brand = brandRepository.findById(requestDto.brandId())
                .orElseThrow(() ->new ResourceNotFoundException("Brand Not found"));

        String slug = SlugUtils.toSlug(requestDto.name());

        Product product = new Product();

        product.setName(requestDto.name());
        product.setSlug(slug);
        product.setSku(requestDto.sku());
        product.setDescription(requestDto.description());
        product.setPrice(requestDto.price());
        product.setMrp(requestDto.mrp());
        product.setCategory(category);
        product.setSeller(user);
        product.setBrand(brand);
        product.setActive(requestDto.active());
        product.setAvgRating(BigDecimal.valueOf(0));
        product.setReviewCount(0);

        product = productRepository.save(product);

        return AddProductResponseDto.from(product);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long productId, UpdateProductRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        Category category = categoryRepository.findById(requestDto.categoryId())
                .orElseThrow(() ->new ResourceNotFoundException("Category Not found"));

        Brand brand = brandRepository.findById(requestDto.brandId())
                .orElseThrow(() ->new ResourceNotFoundException("Brand Not found"));

        product.setName(requestDto.name());
        product.setSku(requestDto.sku());
        product.setDescription(requestDto.description());
        product.setPrice(requestDto.price());
        product.setMrp(requestDto.mrp());
        product.setCategory(category);
        product.setBrand(brand);
        product.setActive(requestDto.active());

        product = productRepository.save(product);

        return ProductResponseDto.from(product);

    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        productRepository.delete(product);
    }

    private User getUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User Not found"));
    }

    private Sort getSort(String sort) {

        if (sort == null || sort.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        return switch (sort.toLowerCase()) {

            case "price_asc" ->
                    Sort.by(Sort.Direction.ASC, "price");

            case "price_desc" ->
                    Sort.by(Sort.Direction.DESC, "price");

            case "name_asc" ->
                    Sort.by(Sort.Direction.ASC, "name");

            case "name_desc" ->
                    Sort.by(Sort.Direction.DESC, "name");

            case "rating_desc" ->
                    Sort.by(Sort.Direction.DESC, "avg_rating");

            case "newest" ->
                    Sort.by(Sort.Direction.DESC, "created_at");

            default ->
                    Sort.by(Sort.Direction.DESC, "created_at");
        };
    }

}
