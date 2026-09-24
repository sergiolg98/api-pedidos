package com.curso.pedidos.product.infrastructure.config;

import com.curso.pedidos.product.application.port.out.CategoryLookupPort;
import com.curso.pedidos.product.application.port.out.ProductRepositoryPort;
import com.curso.pedidos.product.application.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cableado del módulo de productos: aquí (y solo aquí) Spring conoce el servicio
 * de aplicación y le inyecta los adaptadores que implementan sus puertos de salida.
 */
@Configuration
public class ProductBeanConfig {

    @Bean
    public ProductService productService(ProductRepositoryPort productRepository,
                                         CategoryLookupPort categoryLookup) {
        return new ProductService(productRepository, categoryLookup);
    }
}
