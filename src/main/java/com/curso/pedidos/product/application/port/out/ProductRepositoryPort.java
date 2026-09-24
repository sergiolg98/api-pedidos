package com.curso.pedidos.product.application.port.out;

import com.curso.pedidos.product.domain.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: lo que la aplicación necesita de la persistencia de productos.
 * Lo implementa un adaptador (hoy JPA), sin que el dominio lo sepa.
 */
public interface ProductRepositoryPort {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    List<Product> findByNameContaining(String name);

    List<Product> findByPriceGreaterThanAndStockGreaterThan(BigDecimal price, Integer stock);

    List<Product> findAvailable();

    List<Product> findByCategoryName(String categoryName);
}
