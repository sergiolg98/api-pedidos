package com.curso.pedidos.product.application.port.in;

import com.curso.pedidos.product.domain.model.Product;

/**
 * Puerto de entrada: crear un producto.
 */
public interface CreateProductUseCase {

    Product create(CreateProductCommand command);
}
