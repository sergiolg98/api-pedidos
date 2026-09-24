package com.curso.pedidos.customer.infrastructure.adapter.in;

import com.curso.pedidos.customer.application.port.in.CreateCustomerUseCase;
import com.curso.pedidos.customer.application.port.in.GetCustomerUseCase;
import com.curso.pedidos.customer.domain.model.Customer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;

    public CustomerController(
            CreateCustomerUseCase createCustomerUseCase,
            GetCustomerUseCase getCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create(@Valid @RequestBody CustomerDto request) {
        return CustomerWebMapper.toCustomerDto(createCustomerUseCase.create(CustomerWebMapper.toCommand(request)));
    }

    @GetMapping
    public List<CustomerDto> findAll() {
        return CustomerWebMapper.toListOfCustomerDto(this.getCustomerUseCase.findAll());
    }

    @GetMapping("/{id}")
    public CustomerDto findById(@PathVariable Long id) {
        return CustomerWebMapper.toCustomerDto(getCustomerUseCase.findById(id));
    }

}
