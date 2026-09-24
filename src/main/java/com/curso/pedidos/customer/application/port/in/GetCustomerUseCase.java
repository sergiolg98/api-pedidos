package com.curso.pedidos.customer.application.port.in;

import com.curso.pedidos.customer.domain.model.Customer;

import java.util.List;

public interface GetCustomerUseCase {
    Customer findById(Long id);
    List<Customer> findAll();
}
