package com.curso.pedidos.product.domain.exception;

import com.curso.pedidos.exception.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {

    public ProductNotFoundException(Long id) {
        super("Producto no encontrado: " + id);
    }
}
