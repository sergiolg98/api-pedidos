package com.curso.pedidos.product.infrastructure.adapter.out.persistence;

import com.curso.pedidos.entity.Category;
import com.curso.pedidos.product.application.port.out.ProductRepositoryPort;
import com.curso.pedidos.product.domain.exception.CategoryNotFoundException;
import com.curso.pedidos.product.domain.model.Product;
import com.curso.pedidos.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Adaptador de salida: implementa ProductRepositoryPort usando Spring Data JPA.
 */
@Component
public class ProductPersistenceAdapter implements ProductRepositoryPort {

    private final ProductJpaRepository productJpaRepository;
    private final CategoryRepository categoryRepository;

    public ProductPersistenceAdapter(ProductJpaRepository productJpaRepository,
                                     CategoryRepository categoryRepository) {
        this.productJpaRepository = productJpaRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product save(Product product) {
        Long categoryId = product.getCategory().id();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        ProductEntity saved = productJpaRepository.save(ProductPersistenceMapper.toEntity(product, category));
        return ProductPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id).map(ProductPersistenceMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return toDomain(productJpaRepository.findAll());
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return toDomain(productJpaRepository.findByNameContainingIgnoreCase(name));
    }

    @Override
    public List<Product> findByPriceGreaterThanAndStockGreaterThan(BigDecimal price, Integer stock) {
        return toDomain(productJpaRepository.findByPriceGreaterThanAndStockGreaterThan(price, stock));
    }

    @Override
    public List<Product> findAvailable() {
        return toDomain(productJpaRepository.findAvailableProducts());
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        return toDomain(productJpaRepository.findByCategoryName(categoryName));
    }

    private List<Product> toDomain(List<ProductEntity> entities) {
        return entities.stream()
                .map(ProductPersistenceMapper::toDomain)
                .toList();
    }
}
