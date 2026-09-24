package com.curso.pedidos.product.infrastructure.adapter.in.web;

import com.curso.pedidos.product.application.port.in.CreateProductUseCase;
import com.curso.pedidos.product.application.port.in.GetProductUseCase;
import com.curso.pedidos.product.application.port.in.SearchProductsUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Adaptador de entrada (REST). Depende de los puertos de entrada, no de la implementación.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final SearchProductsUseCase searchProductsUseCase;

    public ProductController(CreateProductUseCase createProductUseCase,
                             GetProductUseCase getProductUseCase,
                             SearchProductsUseCase searchProductsUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.getProductUseCase = getProductUseCase;
        this.searchProductsUseCase = searchProductsUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return ProductWebMapper.toResponse(createProductUseCase.create(ProductWebMapper.toCommand(request)));
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return ProductWebMapper.toResponse(getProductUseCase.findAll());
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return ProductWebMapper.toResponse(getProductUseCase.findById(id));
    }

    @GetMapping("/search")
    public List<ProductResponse> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) Integer minStock) {
        return ProductWebMapper.toResponse(searchProductsUseCase.search(name, minPrice, minStock));
    }

    @GetMapping("/available")
    public List<ProductResponse> findAvailable() {
        return ProductWebMapper.toResponse(searchProductsUseCase.findAvailable());
    }

    @GetMapping("/by-category")
    public List<ProductResponse> findByCategory(@RequestParam String categoryName) {
        return ProductWebMapper.toResponse(searchProductsUseCase.findByCategoryName(categoryName));
    }
}
