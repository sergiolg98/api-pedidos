package com.curso.pedidos.service;

import com.curso.pedidos.dto.CategoryDto;
import com.curso.pedidos.entity.Category;
import com.curso.pedidos.exception.ResourceNotFoundException;
import com.curso.pedidos.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto create(CategoryDto request) {
        Category category = new Category(request.getName());
        Category saved = categoryRepository.save(category);
        return toDto(saved);
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + id));
        return toDto(category);
    }

    private CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
