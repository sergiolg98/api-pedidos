package com.curso.pedidos.product.infrastructure.adapter.out.persistence;

import com.curso.pedidos.entity.Category;
import com.curso.pedidos.product.domain.model.Product;
import com.curso.pedidos.product.domain.model.ProductCategory;

/**
 * Traduce entre el modelo de dominio y el modelo JPA.
 */
final class ProductPersistenceMapper {

    private ProductPersistenceMapper() {
    }

    static Product toDomain(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStock(),
                toDomain(entity.getCategory())
        );
    }

    static ProductCategory toDomain(Category category) {
        return new ProductCategory(category.getId(), category.getName());
    }

    static ProductEntity toEntity(Product product, Category category) {
        ProductEntity entity = new ProductEntity(product.getName(), product.getPrice(), product.getStock(), category);
        entity.setId(product.getId());
        return entity;
    }
}
