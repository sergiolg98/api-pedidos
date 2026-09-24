package com.curso.pedidos.product.application.service;

import com.curso.pedidos.product.application.port.in.CreateProductCommand;
import com.curso.pedidos.product.application.port.in.CreateProductUseCase;
import com.curso.pedidos.product.application.port.in.GetProductUseCase;
import com.curso.pedidos.product.application.port.in.SearchProductsUseCase;
import com.curso.pedidos.product.application.port.out.CategoryLookupPort;
import com.curso.pedidos.product.application.port.out.ProductRepositoryPort;
import com.curso.pedidos.product.domain.exception.CategoryNotFoundException;
import com.curso.pedidos.product.domain.exception.ProductNotFoundException;
import com.curso.pedidos.product.domain.model.Product;
import com.curso.pedidos.product.domain.model.ProductCategory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementa los casos de uso de productos. Solo depende de puertos:
 * no hay anotaciones de Spring aquí, el bean se registra en ProductBeanConfig.
 */
public class ProductService implements CreateProductUseCase, GetProductUseCase, SearchProductsUseCase {

    private final ProductRepositoryPort productRepository;
    private final CategoryLookupPort categoryLookup;

    public ProductService(ProductRepositoryPort productRepository, CategoryLookupPort categoryLookup) {
        this.productRepository = productRepository;
        this.categoryLookup = categoryLookup;
    }

    @Override
    public Product create(CreateProductCommand command) {
        ProductCategory category = categoryLookup.findById(command.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(command.categoryId()));

        Product product = Product.create(command.name(), command.price(), command.stock(), category);
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> search(String name, BigDecimal minPrice, Integer minStock) {
        if (name != null) {
            return productRepository.findByNameContaining(name);
        }
        if (minPrice != null && minStock != null) {
            return productRepository.findByPriceGreaterThanAndStockGreaterThan(minPrice, minStock);
        }
        return findAll();
    }

    @Override
    public List<Product> findAvailable() {
        return productRepository.findAvailable();
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }
}
