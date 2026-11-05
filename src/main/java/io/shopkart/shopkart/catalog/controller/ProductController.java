package io.shopkart.shopkart.catalog.controller;

import io.shopkart.shopkart.catalog.dto.AddProductRequestDto;
import io.shopkart.shopkart.catalog.dto.ProductListRequestDto;
import io.shopkart.shopkart.catalog.dto.ProductListResponseDto;
import io.shopkart.shopkart.catalog.service.ProductService;
import io.shopkart.shopkart.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ProductListResponseDto>> productList(@RequestBody ProductListRequestDto requestDto) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductList(requestDto)));
    }

    @GetMapping("/{productId}")
    public void productDetail(@PathVariable Long productId) {

    }

    @GetMapping("/slug/{slug}")
    public void productDetailBySlug(@PathVariable String slug) {

    }

    @PostMapping
    public void addProduct(
            Authentication authentication,
            @RequestBody AddProductRequestDto requestDto
    ) {



    }

    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable Long productId) {

    }

    @DeleteMapping("/productId")
    public void deleteProduct(@PathVariable Long productId) {

    }

    @PostMapping("/{productId}/images")
    public void uploadProductImages(@PathVariable Long productId) {

    }

    @PostMapping("/{productId}/images/{imageId}")
    public void deleteProductImages(@PathVariable Long productId, @PathVariable Long imageId) {

    }

}
