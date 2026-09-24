package com.curso.pedidos.customer.infrastructure.adapter.out;

import com.curso.pedidos.customer.domain.model.Customer;
import com.curso.pedidos.customer.infrastructure.entities.CustomerEntity;

public class CustomerPersistenceMapper {

    public static CustomerEntity toCustomerEntity(Customer customer) {
        CustomerEntity entity = new CustomerEntity(customer.getName(), customer.getEmail());
        entity.setId(customer.getId());
        return entity;
    }

    public static Customer toCustomer(CustomerEntity entity) {
        return new Customer(entity.getId(), entity.getName(), entity.getEmail());
    }

}
