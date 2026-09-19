package com.curso.pedidos.service;

import com.curso.pedidos.dto.CustomerDto;
import com.curso.pedidos.entity.Customer;
import com.curso.pedidos.exception.ResourceNotFoundException;
import com.curso.pedidos.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDto create(CustomerDto request) {
        Customer customer = new Customer(request.getName(), request.getEmail());
        Customer saved = customerRepository.save(customer);
        return toDto(saved);
    }

    public List<CustomerDto> findAll() {
        return customerRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public CustomerDto findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + id));
        return toDto(customer);
    }

    private CustomerDto toDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName(), customer.getEmail());
    }
}
