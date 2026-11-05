package io.shopkart.shopkart.catalog.service;

import io.shopkart.shopkart.catalog.Entity.Category;
import io.shopkart.shopkart.catalog.dto.*;
import io.shopkart.shopkart.catalog.repository.CategoryRepository;
import io.shopkart.shopkart.catalog.repository.ProductRepository;
import io.shopkart.shopkart.common.exception.NotAllowedException;
import io.shopkart.shopkart.common.exception.ResourceNotFoundException;
import io.shopkart.shopkart.common.utils.SlugUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public CategoryService(
            CategoryRepository categoryRepository,
            ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public CategoryListResponseDto getCategories() {
        List<Category> categories = categoryRepository.findByParentIsNull();

        List<CategoryResponseDto> categoryResponseDtoList
                = categories.stream().map(category -> CategoryResponseDto.from(category))
                .toList();

        return new CategoryListResponseDto(categoryResponseDtoList);
    }

    @Transactional
    public AddCategoryResponseDto createCategory(AddCategoryRequestDto requestDto) {

        String slug = SlugUtils.toSlug(requestDto.name());

        Category category = new Category();
        category.setName(requestDto.name());
        category.setDescription(requestDto.description());
        category.setSlug(slug);
        category.setActive(category.isActive());

        Long parentId = requestDto.parentId();
        if (parentId != 0) {
            Category parentCategory = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Parent Category Not Found!"));

            category.setParent(parentCategory);
            parentCategory.addChild(category);
            categoryRepository.save(parentCategory);
        }

        category = categoryRepository.save(category);

        return AddCategoryResponseDto.from(category);
    }

    @Transactional
    public CategoryResponseDto updateCategory(Long categoryId, UpdateCategoryRequestDto requestDto) {

        Category category = getCategoryById(categoryId);

        category.setName(requestDto.name());
        category.setDescription(requestDto.description());
        category.setActive(requestDto.active());

        if (requestDto.parentId() != 0) {
            Category parentCategory = getCategoryById(requestDto.parentId());
            category.setParent(parentCategory);
        }

        category = categoryRepository.save(category);
        return CategoryResponseDto.from(category);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {

        Category category = getCategoryById(categoryId);

        if (categoryRepository.existsByParentId(categoryId)) {
            throw new NotAllowedException(
                    "Cannot delete category because it has child categories"
            );
        }

        if (productRepository.existsByCategoryId(categoryId)) {
            throw new NotAllowedException(
                    "Cannot delete category because it has associated products"
            );
        }

        categoryRepository.delete(category);
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + categoryId
                ));
    }
}
