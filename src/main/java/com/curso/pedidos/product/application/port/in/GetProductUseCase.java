package com.curso.pedidos.product.application.port.in;

import com.curso.pedidos.product.domain.model.Product;

import java.util.List;

/**
 * Puerto de entrada: consultar productos por id o listarlos todos.
 */
public interface GetProductUseCase {

    Product findById(Long id);

    List<Product> findAll();
}
