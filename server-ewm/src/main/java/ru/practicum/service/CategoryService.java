package ru.practicum.service;

import ru.practicum.dto.CategoryDto;
import ru.practicum.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto);

    List<CategoryDto> getAll(int from, int size);

    CategoryDto getById(Long id);

    void delete(Long id);

    CategoryDto update(Long id, CategoryDto categoryDto);

    Category getCategory(Long id);

    List<Category> getAllById(List<Long> ids);
}
