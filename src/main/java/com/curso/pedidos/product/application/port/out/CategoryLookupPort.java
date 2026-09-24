package com.curso.pedidos.product.application.port.out;

import com.curso.pedidos.product.domain.model.ProductCategory;

import java.util.Optional;

/**
 * Puerto de salida: obtener la categoría a la que se asigna un producto.
 */
public interface CategoryLookupPort {

    Optional<ProductCategory> findById(Long id);
}
