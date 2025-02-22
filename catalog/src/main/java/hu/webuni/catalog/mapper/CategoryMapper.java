package hu.webuni.catalog.mapper;

import hu.webuni.catalog.api.model.CategoryDto;
import hu.webuni.catalog.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto categoryToDto(Category category);
    Category dtoToCategory(CategoryDto categoryDto);
}