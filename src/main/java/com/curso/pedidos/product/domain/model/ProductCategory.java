package com.curso.pedidos.product.domain.model;

/**
 * Lo que el dominio de productos necesita saber de una categoría.
 * No es la entidad Category de JPA: es un value object propio del módulo.
 */
public record ProductCategory(Long id, String name) {
}
