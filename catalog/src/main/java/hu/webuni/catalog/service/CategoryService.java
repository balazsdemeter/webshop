package hu.webuni.catalog.service;

import hu.webuni.catalog.api.model.CategoryDto;
import hu.webuni.catalog.mapper.CategoryMapper;
import hu.webuni.catalog.model.Category;
import hu.webuni.catalog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryMapper.dtoToCategory(categoryDto);
        return categoryMapper.categoryToDto(categoryRepository.save(category));
    }

    public Category findById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}