package com.curso.pedidos.product.application.port.in;

import com.curso.pedidos.product.domain.model.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * Puerto de entrada: búsquedas y filtros sobre productos.
 */
public interface SearchProductsUseCase {

    List<Product> search(String name, BigDecimal minPrice, Integer minStock);

    List<Product> findAvailable();

    List<Product> findByCategoryName(String categoryName);
}
