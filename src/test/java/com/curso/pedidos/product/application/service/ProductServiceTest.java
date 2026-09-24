package com.curso.pedidos.product.application.service;

import com.curso.pedidos.product.application.port.in.CreateProductCommand;
import com.curso.pedidos.product.application.port.out.CategoryLookupPort;
import com.curso.pedidos.product.application.port.out.ProductRepositoryPort;
import com.curso.pedidos.product.domain.exception.CategoryNotFoundException;
import com.curso.pedidos.product.domain.exception.ProductNotFoundException;
import com.curso.pedidos.product.domain.model.Product;
import com.curso.pedidos.product.domain.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Prueba el servicio de aplicación sin Spring ni base de datos:
 * los puertos de salida se sustituyen por implementaciones en memoria.
 */
class ProductServiceTest {

    private static final ProductCategory TECNOLOGIA = new ProductCategory(1L, "Tecnología");
    private static final ProductCategory HOGAR = new ProductCategory(2L, "Hogar");

    private InMemoryProductRepository productRepository;
    private ProductService service;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        CategoryLookupPort categoryLookup = id -> Optional.ofNullable(Map.of(1L, TECNOLOGIA, 2L, HOGAR).get(id));
        service = new ProductService(productRepository, categoryLookup);
    }

    @Test
    void createAssignsIdAndCategory() {
        Product created = service.create(new CreateProductCommand("Teclado", new BigDecimal("59.90"), 15, 1L));

        assertNotNull(created.getId());
        assertEquals("Tecnología", created.getCategory().name());
    }

    @Test
    void createFailsWhenCategoryDoesNotExist() {
        assertThrows(CategoryNotFoundException.class,
                () -> service.create(new CreateProductCommand("Teclado", BigDecimal.TEN, 1, 99L)));
    }

    @Test
    void findByIdFailsWhenProductDoesNotExist() {
        assertThrows(ProductNotFoundException.class, () -> service.findById(42L));
    }

    @Test
    void searchByNameTakesPrecedence() {
        service.create(new CreateProductCommand("Teclado mecánico", new BigDecimal("59.90"), 15, 1L));
        service.create(new CreateProductCommand("Mouse", new BigDecimal("29.90"), 30, 1L));

        List<Product> result = service.search("teclado", BigDecimal.ONE, 1);

        assertEquals(1, result.size());
        assertEquals("Teclado mecánico", result.get(0).getName());
    }

    @Test
    void searchWithoutFiltersReturnsAll() {
        service.create(new CreateProductCommand("Teclado", new BigDecimal("59.90"), 15, 1L));
        service.create(new CreateProductCommand("Lámpara", new BigDecimal("24.50"), 20, 2L));

        assertEquals(2, service.search(null, null, null).size());
    }

    @Test
    void findAvailableExcludesProductsWithoutStock() {
        service.create(new CreateProductCommand("Teclado", new BigDecimal("59.90"), 15, 1L));
        service.create(new CreateProductCommand("Monitor", new BigDecimal("189.00"), 0, 1L));

        List<Product> result = service.findAvailable();

        assertEquals(1, result.size());
        assertEquals("Teclado", result.get(0).getName());
    }

    private static class InMemoryProductRepository implements ProductRepositoryPort {

        private final List<Product> products = new ArrayList<>();
        private long nextId = 1;

        @Override
        public Product save(Product product) {
            Product saved = new Product(nextId++, product.getName(), product.getPrice(),
                    product.getStock(), product.getCategory());
            products.add(saved);
            return saved;
        }

        @Override
        public Optional<Product> findById(Long id) {
            return products.stream().filter(p -> p.getId().equals(id)).findFirst();
        }

        @Override
        public List<Product> findAll() {
            return List.copyOf(products);
        }

        @Override
        public List<Product> findByNameContaining(String name) {
            return products.stream()
                    .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                    .toList();
        }

        @Override
        public List<Product> findByPriceGreaterThanAndStockGreaterThan(BigDecimal price, Integer stock) {
            return products.stream()
                    .filter(p -> p.getPrice().compareTo(price) > 0 && p.getStock() > stock)
                    .toList();
        }

        @Override
        public List<Product> findAvailable() {
            return products.stream().filter(Product::hasStock).toList();
        }

        @Override
        public List<Product> findByCategoryName(String categoryName) {
            return products.stream()
                    .filter(p -> p.getCategory().name().equals(categoryName))
                    .toList();
        }
    }
}
