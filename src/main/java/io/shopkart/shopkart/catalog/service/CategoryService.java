package io.shopkart.shopkart.catalog.service;

import io.shopkart.shopkart.catalog.Entity.Category;
import io.shopkart.shopkart.catalog.dto.AddCategoryRequestDto;
import io.shopkart.shopkart.catalog.dto.AddCategoryResponseDto;
import io.shopkart.shopkart.catalog.dto.CategoryListResponseDto;
import io.shopkart.shopkart.catalog.dto.CategoryResponseDto;
import io.shopkart.shopkart.catalog.repository.CategoryRepository;
import io.shopkart.shopkart.common.utils.SlugUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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

}
