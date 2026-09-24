package com.curso.pedidos.product.domain.exception;

import com.curso.pedidos.exception.ResourceNotFoundException;

public class CategoryNotFoundException extends ResourceNotFoundException {

    public CategoryNotFoundException(Long id) {
        super("Categoría no encontrada: " + id);
    }
}
