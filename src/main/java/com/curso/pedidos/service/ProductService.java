package com.curso.pedidos.service;

import com.curso.pedidos.dto.ProductRequest;
import com.curso.pedidos.dto.ProductResponse;
import com.curso.pedidos.entity.Category;
import com.curso.pedidos.entity.Product;
import com.curso.pedidos.exception.ResourceNotFoundException;
import com.curso.pedidos.repository.CategoryRepository;
import com.curso.pedidos.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse create(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + request.getCategoryId()));

        Product product = new Product(request.getName(), request.getPrice(), request.getStock(), category);
        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
        return toResponse(product);
    }

    public List<ProductResponse> search(String name, BigDecimal minPrice, Integer minStock) {
        if (name != null) {
            return productRepository.findByNameContainingIgnoreCase(name).stream()
                    .map(this::toResponse)
                    .toList();
        }
        if (minPrice != null && minStock != null) {
            return productRepository.findByPriceGreaterThanAndStockGreaterThan(minPrice, minStock).stream()
                    .map(this::toResponse)
                    .toList();
        }
        return findAll();
    }

    public List<ProductResponse> findAvailable() {
        return productRepository.findAvailableProducts().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProductResponse> findByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName).stream()
                .map(this::toResponse)
                .toList();
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }
}
