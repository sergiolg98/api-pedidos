package com.curso.pedidos.product.application.port.in;

import java.math.BigDecimal;

/**
 * Datos de entrada del caso de uso "crear producto".
 * Independiente del DTO HTTP: el adaptador web lo construye a partir del request.
 */
public record CreateProductCommand(String name, BigDecimal price, Integer stock, Long categoryId) {
}
