package com.curso.pedidos.customer.application.port.in;

import com.curso.pedidos.customer.domain.model.Customer;

public interface CreateCustomerUseCase {
    Customer create(CreateCustomerCommand cmd);
}
