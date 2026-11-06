package io.shopkart.shopkart.catalog.controller;

import io.shopkart.shopkart.catalog.dto.*;
import io.shopkart.shopkart.catalog.service.ProductService;
import io.shopkart.shopkart.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<ApiResponse<ProductResponseDto>> productDetail(@PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductDetailById(productId)));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> productDetailBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductDetailBySlug(slug)));
    }

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<AddProductResponseDto>> addProduct(
            Authentication authentication,
            @Valid @RequestBody AddProductRequestDto requestDto
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(productService.addProduct(
                        authentication, requestDto
                )));
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductRequestDto productRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(productService.updateProduct(
                        productId,
                        productRequestDto
                )));
    }

    @DeleteMapping("/productId")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success("Product deleted successfully"));
    }

    @PostMapping("/{productId}/images")
    @PreAuthorize("hasRole('SELLER')")
    public void uploadProductImages(@PathVariable Long productId) {

    }

    @PostMapping("/{productId}/images/{imageId}")
    @PreAuthorize("hasRole('SELLER')")
    public void deleteProductImages(@PathVariable Long productId, @PathVariable Long imageId) {

    }

}
