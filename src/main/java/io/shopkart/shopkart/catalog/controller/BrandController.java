package io.shopkart.shopkart.catalog.controller;

import io.shopkart.shopkart.catalog.dto.AddBrandRequestDto;
import io.shopkart.shopkart.catalog.dto.BrandListResponseDto;
import io.shopkart.shopkart.catalog.dto.BrandResponseDto;
import io.shopkart.shopkart.catalog.service.BrandService;
import io.shopkart.shopkart.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<BrandListResponseDto>> getBrandList() {
        return ResponseEntity.ok(ApiResponse.success(brandService.getAllBrands()));
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<ApiResponse<BrandResponseDto>> getBrandDetail(@PathVariable Long brandId) {
        return ResponseEntity.ok(ApiResponse.success(brandService.getBrandDetail(brandId)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BrandResponseDto>> addBrand(
            @Valid @RequestBody AddBrandRequestDto requestDto
            ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(brandService.createBrand(requestDto)));
    }

    @PutMapping("/{brandId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BrandResponseDto>> updateBrand(
            @PathVariable Long brandId,
            @Valid @RequestBody AddBrandRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(brandService.updateBrand(brandId, requestDto)));
    }

    @DeleteMapping("/{brandId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("Brand deleted successfully"));
    }

}
