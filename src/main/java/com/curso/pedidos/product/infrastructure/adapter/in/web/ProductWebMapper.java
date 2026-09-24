package com.curso.pedidos.product.infrastructure.adapter.in.web;

import com.curso.pedidos.product.application.port.in.CreateProductCommand;
import com.curso.pedidos.product.domain.model.Product;

import java.util.List;

/**
 * Traduce entre los DTOs HTTP y el modelo/comandos de la aplicación.
 */
final class ProductWebMapper {

    private ProductWebMapper() {
    }

    static CreateProductCommand toCommand(ProductRequest request) {
        return new CreateProductCommand(
                request.getName(),
                request.getPrice(),
                request.getStock(),
                request.getCategoryId()
        );
    }

    static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().name()
        );
    }

    static List<ProductResponse> toResponse(List<Product> products) {
        return products.stream()
                .map(ProductWebMapper::toResponse)
                .toList();
    }
}
