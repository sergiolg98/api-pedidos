package com.curso.pedidos.customer.application.port.out;

import com.curso.pedidos.customer.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
}
