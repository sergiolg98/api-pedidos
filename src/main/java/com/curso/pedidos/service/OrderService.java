package com.curso.pedidos.service;

import com.curso.pedidos.customer.infrastructure.adapter.out.CustomerJpaRepository;
import com.curso.pedidos.customer.infrastructure.entities.CustomerEntity;
import com.curso.pedidos.dto.OrderItemRequest;
import com.curso.pedidos.dto.OrderItemResponse;
import com.curso.pedidos.dto.OrderRequest;
import com.curso.pedidos.dto.OrderResponse;
import com.curso.pedidos.entity.Order;
import com.curso.pedidos.entity.OrderItem;
import com.curso.pedidos.entity.Product;
import com.curso.pedidos.exception.ResourceNotFoundException;
import com.curso.pedidos.repository.OrderRepository;
import com.curso.pedidos.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * A propósito, esta clase mezcla varias responsabilidades (crear el pedido,
 * "enviar" el email, "generar" la factura, y hablar directamente con
 * JpaRepository). Es el ejemplo vivo de SRP/DIP que refactorizamos en la
 * Clase 2 hacia Arquitectura Hexagonal. No lo "arregles" antes de esa clase.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerJpaRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                         CustomerJpaRepository customerRepository,
                         ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public OrderResponse createOrder(OrderRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + request.getCustomerId()));

        Order order = new Order(customer);

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + itemRequest.getProductId()));

            if (!product.hasStock() || product.getStock() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente para: " + product.getName());
            }

            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);

            order.addItem(new OrderItem(product, itemRequest.getQuantity()));
        }

        Order saved = orderRepository.save(order);

        sendConfirmationEmail(saved);
        generateInvoice(saved);

        return toResponse(saved);
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<OrderResponse> findByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmail(email).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<OrderResponse> findByProductCategory(String categoryName) {
        return orderRepository.findOrdersByProductCategory(categoryName).stream()
                .map(this::toResponse)
                .toList();
    }

    // --- esto no debería vivir aquí: es la responsabilidad que separamos en la Clase 2 ---

    private void sendConfirmationEmail(Order order) {
        System.out.println("Enviando email de confirmación a " + order.getCustomer().getEmail());
    }

    private void generateInvoice(Order order) {
        System.out.println("Generando factura para el pedido #" + order.getId());
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                ))
                .toList();

        BigDecimal total = items.stream()
                .map(OrderItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderResponse(order.getId(), order.getOrderDate(), order.getCustomer().getName(), items, total);
    }
}
