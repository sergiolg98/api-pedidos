package com.curso.pedidos.customer.infrastructure.adapter.in;

import com.curso.pedidos.customer.application.port.in.CreateCustomerCommand;
import com.curso.pedidos.customer.domain.model.Customer;

import java.util.List;

public class CustomerWebMapper {

    public static CustomerDto toCustomerDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }

    public static List<CustomerDto> toListOfCustomerDto(List<Customer> customerList) {
        return customerList.stream()
                .map(customer -> toCustomerDto(customer))
                .toList();
    }

    public static CreateCustomerCommand toCommand(CustomerDto request) {
        return new CreateCustomerCommand(request.getName(), request.getEmail());
    }
}
