package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryDto;
import org.example.dto.NewsDto;
import org.example.entity.Category;
import org.example.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CategoryCRUDService implements CRUDService<CategoryDto> {
    private final CategoryRepository categoryRepo;

    @Override
    public CategoryDto getById(Long id) {
        if (categoryRepo.findById(id).isEmpty()) {
            return null;
        }
        return mapToDto(categoryRepo.findById(id).orElseThrow());
    }

    @Override
    public Collection<CategoryDto> getAll() {
        return categoryRepo.findAll().stream()
                .map(CategoryCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(CategoryDto item) {
        categoryRepo.save(mapToEntity(item));
    }

    @Override
    public void update(CategoryDto item) {
        categoryRepo.save(mapToEntity(item));
    }

    @Override
    public boolean delete(Long id) {
        if (categoryRepo.existsById(id)) {
            categoryRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Collection<NewsDto> getNews(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow();
        return category.getNewsList().stream()
                .map(NewsCRUDService::mapToDto)
                .toList();
    }

    public static CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        return categoryDto;
    }

    public static Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setTitle(categoryDto.getTitle());
        return category;
    }
}
