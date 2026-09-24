package com.curso.pedidos.customer.application.service;

import com.curso.pedidos.customer.application.port.in.CreateCustomerCommand;
import com.curso.pedidos.customer.application.port.in.CreateCustomerUseCase;
import com.curso.pedidos.customer.application.port.in.GetCustomerUseCase;
import com.curso.pedidos.customer.application.port.out.CustomerRepositoryPort;
import com.curso.pedidos.customer.domain.exception.CustomerNotFoundException;
import com.curso.pedidos.customer.domain.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements CreateCustomerUseCase, GetCustomerUseCase {

    CustomerRepositoryPort repository; // puerto salida

    public CustomerService(CustomerRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Customer create(CreateCustomerCommand cmd) {
        Customer cus = new Customer();
        cus.setName(cmd.getName());
        cus.setEmail(cmd.getEmail());

        return this.repository.save(cus);
    }

    @Override
    public Customer findById(Long id) {
        Optional<Customer> optionalCustomer = this.repository.findById(id);
        if(optionalCustomer.isEmpty())
            throw new CustomerNotFoundException("Cliente no encontrado con id " + id);
        return optionalCustomer.get();
    }

    @Override
    public List<Customer> findAll() {
        return this.repository.findAll();
    }
}
