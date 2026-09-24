package com.curso.pedidos.config;

import com.curso.pedidos.entity.Category;
import com.curso.pedidos.entity.Customer;
import com.curso.pedidos.product.application.port.in.CreateProductCommand;
import com.curso.pedidos.product.application.port.in.CreateProductUseCase;
import com.curso.pedidos.repository.CategoryRepository;
import com.curso.pedidos.repository.CustomerRepository;
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
    private final CreateProductUseCase createProductUseCase;
    private final CustomerRepository customerRepository;

    public DataInitializer(CategoryRepository categoryRepository,
                            CreateProductUseCase createProductUseCase,
                            CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.createProductUseCase = createProductUseCase;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) {
            return;
        }

        Category tecnologia = categoryRepository.save(new Category("Tecnología"));
        Category hogar = categoryRepository.save(new Category("Hogar"));

        createProductUseCase.create(new CreateProductCommand("Teclado mecánico", new BigDecimal("59.90"), 15, tecnologia.getId()));
        createProductUseCase.create(new CreateProductCommand("Mouse inalámbrico", new BigDecimal("29.90"), 30, tecnologia.getId()));
        createProductUseCase.create(new CreateProductCommand("Monitor 24 pulgadas", new BigDecimal("189.00"), 0, tecnologia.getId()));
        createProductUseCase.create(new CreateProductCommand("Lámpara de escritorio", new BigDecimal("24.50"), 20, hogar.getId()));

        customerRepository.save(new Customer("Ana Torres", "ana.torres@mail.com"));
        customerRepository.save(new Customer("Luis Ramírez", "luis.ramirez@mail.com"));

        System.out.println("Datos de ejemplo cargados: categorías, productos y clientes.");
    }
}
