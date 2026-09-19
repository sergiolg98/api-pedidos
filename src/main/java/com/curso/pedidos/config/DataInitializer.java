package com.curso.pedidos.config;

import com.curso.pedidos.entity.Category;
import com.curso.pedidos.entity.Customer;
import com.curso.pedidos.entity.Product;
import com.curso.pedidos.repository.CategoryRepository;
import com.curso.pedidos.repository.CustomerRepository;
import com.curso.pedidos.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Carga datos de ejemplo al arrancar, para poder probar los endpoints
 * en clase sin tener que crear todo a mano primero.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public DataInitializer(CategoryRepository categoryRepository,
                            ProductRepository productRepository,
                            CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) {
            return;
        }

        Category tecnologia = categoryRepository.save(new Category("Tecnología"));
        Category hogar = categoryRepository.save(new Category("Hogar"));

        productRepository.save(new Product("Teclado mecánico", new BigDecimal("59.90"), 15, tecnologia));
        productRepository.save(new Product("Mouse inalámbrico", new BigDecimal("29.90"), 30, tecnologia));
        productRepository.save(new Product("Monitor 24 pulgadas", new BigDecimal("189.00"), 0, tecnologia));
        productRepository.save(new Product("Lámpara de escritorio", new BigDecimal("24.50"), 20, hogar));

        customerRepository.save(new Customer("Ana Torres", "ana.torres@mail.com"));
        customerRepository.save(new Customer("Luis Ramírez", "luis.ramirez@mail.com"));

        System.out.println("Datos de ejemplo cargados: categorías, productos y clientes.");
    }
}
