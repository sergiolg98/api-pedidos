package com.curso.pedidos.product.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Modelo de dominio del producto. Es Java puro: no conoce JPA, Spring ni HTTP.
 * Protege sus propias invariantes (nombre, precio y stock válidos).
 */
public class Product {

    private final Long id;
    private final String name;
    private final BigDecimal price;
    private final int stock;
    private final ProductCategory category;

    public Product(Long id, String name, BigDecimal price, Integer stock, ProductCategory category) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (price == null || price.signum() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = Objects.requireNonNull(category, "La categoría es obligatoria");
    }

    /**
     * Crea un producto nuevo, todavía sin identificador (lo asigna la persistencia).
     */
    public static Product create(String name, BigDecimal price, Integer stock, ProductCategory category) {
        return new Product(null, name, price, stock, category);
    }

    public boolean hasStock() {
        return stock > 0;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public ProductCategory getCategory() {
        return category;
    }
}
