package com.curso.pedidos.customer.infrastructure.adapter.out;

import com.curso.pedidos.customer.infrastructure.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// adaptador de MySQL por medio de JPA
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity,Long> {
}
