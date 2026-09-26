package com.curso.pedidos.customer.application.service;

import com.curso.pedidos.customer.application.port.in.CreateCustomerCommand;
import com.curso.pedidos.customer.application.port.out.CustomerRepositoryPort;
import com.curso.pedidos.customer.domain.exception.CustomerNotFoundException;
import com.curso.pedidos.customer.domain.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    CustomerRepositoryPort repository;

    @InjectMocks
    CustomerService customerService;

    // AAA - > Arrange, Act , Assert

    @Test
    void shouldThrowWhenCustomerNotExists() {
        // Arrange
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.findById(99L));
    }


    @Test
    void shouldReturnCustomerIfExists() {
        // Arrange
        Long customerId = 10L;
        Customer customer = new Customer(customerId, "Fiorella", "fio@mail.com");
        when(repository.findById(customerId))
                .thenReturn(Optional.of(customer));

        // Act
        Customer valueReturned = customerService.findById(customerId);

        // Assert
        assertNotNull(valueReturned);
        assertNotNull(valueReturned.getId());
        assertEquals("Fiorella", valueReturned.getName());

        // matchers eq, any
        verify(repository).findById(eq(customerId));
        verify(repository, never()).save(any(Customer.class));
    }

    @Test
    void shouldSaveCustomerOnce() {
        Long customerId = 10L;
        Customer customer = new Customer(customerId, "Fiorella", "fio@mail.com");

        when(repository.save(any(Customer.class)))
                .thenReturn(customer);

        customerService.create(new CreateCustomerCommand("Fiorella", "fio@mail.com"));
        verify(repository, times(1))
                .save(any(Customer.class));
    }
}
