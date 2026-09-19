package com.curso.pedidos.controller;

import com.curso.pedidos.dto.OrderRequest;
import com.curso.pedidos.dto.OrderResponse;
import com.curso.pedidos.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@Valid @RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/by-customer-email")
    public List<OrderResponse> findByCustomerEmail(@RequestParam String email) {
        return orderService.findByCustomerEmail(email);
    }

    @GetMapping("/by-category")
    public List<OrderResponse> findByCategory(@RequestParam String categoryName) {
        return orderService.findByProductCategory(categoryName);
    }
}
