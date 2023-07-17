package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.dto.CategoryDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.model.Category;
import ru.practicum.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = mapper.toCategory(categoryDto);
        return mapper.toCategoryDto(repository.save(category));
    }

    @Override
    public List<CategoryDto> getAll(int from, int size) {
        int pageNumber = (int) Math.ceil((double) from / size);
        return repository.findAll(PageRequest.of(pageNumber, size, Sort.by("id").ascending()))
                .stream().map(mapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long id) {
        return mapper.toCategoryDto(getCategory(id));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category category = getCategory(id);
        category.setName(categoryDto.getName());
        return mapper.toCategoryDto(repository.save(category));
    }

    @Override
    public Category getCategory(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Категории с id %d не найдено", id)));
    }

    @Override
    public List<Category> getAllById(List<Long> ids) {
        return repository.findAllById(ids);
    }
}
