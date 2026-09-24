package com.curso.pedidos.product.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositorio Spring Data. Solo lo usa el adaptador de persistencia
 * (y, por ahora, el código legado de pedidos).
 */
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByNameContainingIgnoreCase(String name);

    List<ProductEntity> findByPriceGreaterThanAndStockGreaterThan(BigDecimal price, Integer stock);

    List<ProductEntity> findByCategoryName(String categoryName);

    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    List<ProductEntity> findAvailableProducts();
}
