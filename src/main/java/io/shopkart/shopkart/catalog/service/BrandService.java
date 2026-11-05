package io.shopkart.shopkart.catalog.service;

import io.shopkart.shopkart.catalog.Entity.Brand;
import io.shopkart.shopkart.catalog.dto.AddBrandRequestDto;
import io.shopkart.shopkart.catalog.dto.BrandListResponseDto;
import io.shopkart.shopkart.catalog.dto.BrandResponseDto;
import io.shopkart.shopkart.catalog.repository.BrandRepository;
import io.shopkart.shopkart.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandService {

    private BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public BrandListResponseDto getAllBrands() {

        List<Brand> brands = brandRepository.findAll();
        return new BrandListResponseDto(brands.stream()
                .map(BrandResponseDto::from).toList());
    }

    public BrandResponseDto getBrandDetail(Long brandId) {
        Brand brand = geBrandById(brandId);
        return BrandResponseDto.from(brand);
    }

    @Transactional
    public BrandResponseDto createBrand(AddBrandRequestDto requestDto) {
        Brand brand = new Brand();
        brand.setName(requestDto.name());
        brand.setLogoUrl(requestDto.logoUrl());

        brand = brandRepository.save(brand);
        return BrandResponseDto.from(brand);
    }

    @Transactional
    public BrandResponseDto updateBrand(Long brandId, AddBrandRequestDto requestDto) {
        Brand brand = geBrandById(brandId);
        brand.setName(requestDto.name());
        brand.setLogoUrl(requestDto.logoUrl());

        brand = brandRepository.save(brand);
        return BrandResponseDto.from(brand);
    }

    @Transactional
    public void deleteBrand(Long brandId) {
        Brand brand = geBrandById(brandId);
        brandRepository.delete(brand);
    }

    private Brand geBrandById(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found by id: " + brandId));
    }

}
