package io.shopkart.shopkart.catalog.controller;

import io.shopkart.shopkart.catalog.Entity.Category;
import io.shopkart.shopkart.catalog.dto.AddCategoryRequestDto;
import io.shopkart.shopkart.catalog.dto.AddCategoryResponseDto;
import io.shopkart.shopkart.catalog.dto.CategoryListResponseDto;
import io.shopkart.shopkart.catalog.service.CategoryService;
import io.shopkart.shopkart.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CategoryListResponseDto>> getCategories() {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getCategories()));
    }

    @GetMapping("/{categoryId}/products")
    public void getCategoryProducts(@PathVariable Long categoryId) {

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AddCategoryResponseDto>> addCategory(
            @Valid @RequestBody AddCategoryRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(categoryService.createCategory(requestDto)));
    }

    @PutMapping
    public void updateCategory() {

    }

    @DeleteMapping
    public void deleteCategory() {

    }

}
