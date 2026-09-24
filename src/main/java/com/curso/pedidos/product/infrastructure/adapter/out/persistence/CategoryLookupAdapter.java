package com.curso.pedidos.product.infrastructure.adapter.out.persistence;

import com.curso.pedidos.product.application.port.out.CategoryLookupPort;
import com.curso.pedidos.product.domain.model.ProductCategory;
import com.curso.pedidos.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Adaptador de salida: resuelve categorías usando el repositorio JPA existente.
 */
@Component
public class CategoryLookupAdapter implements CategoryLookupPort {

    private final CategoryRepository categoryRepository;

    public CategoryLookupAdapter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<ProductCategory> findById(Long id) {
        return categoryRepository.findById(id).map(ProductPersistenceMapper::toDomain);
    }
}
