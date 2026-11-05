package io.shopkart.shopkart.catalog.service;

import io.shopkart.shopkart.catalog.Entity.Brand;
import io.shopkart.shopkart.catalog.Entity.Category;
import io.shopkart.shopkart.catalog.Entity.Product;
import io.shopkart.shopkart.catalog.dto.AddProductRequestDto;
import io.shopkart.shopkart.catalog.dto.AddProductResponseDto;
import io.shopkart.shopkart.catalog.dto.ProductListRequestDto;
import io.shopkart.shopkart.catalog.dto.ProductListResponseDto;
import io.shopkart.shopkart.catalog.repository.BrandRepository;
import io.shopkart.shopkart.catalog.repository.CategoryRepository;
import io.shopkart.shopkart.catalog.repository.ProductRepository;
import io.shopkart.shopkart.common.exception.UserNotFoundException;
import io.shopkart.shopkart.user.Entity.User;
import io.shopkart.shopkart.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
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
            CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductListResponseDto getProductList(ProductListRequestDto requestDto) {
        List<Product> productList = productRepository.findAll();

        return ProductListResponseDto.from(requestDto, productList);
    }

    @Transactional
    public AddProductResponseDto addProduct(Authentication authentication, AddProductRequestDto requestDto) {

        User user = getUser(authentication);

        Category category = categoryRepository.findById(requestDto.categoryId())
                .orElseThrow(() ->new RuntimeException("Category Not found"));

        Brand brand = brandRepository.findById(requestDto.brandId())
                .orElseThrow(() ->new RuntimeException("Brand Not found"));

        Product product = new Product();

        product.setName(requestDto.name());
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

    private User getUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User Not found"));
    }

}
