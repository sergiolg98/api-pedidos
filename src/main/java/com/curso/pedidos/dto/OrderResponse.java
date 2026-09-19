package com.curso.pedidos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long id;
    private LocalDateTime orderDate;
    private String customerName;
    private List<OrderItemResponse> items;
    private BigDecimal total;

    public OrderResponse() {
    }

    public OrderResponse(Long id, LocalDateTime orderDate, String customerName,
                          List<OrderItemResponse> items, BigDecimal total) {
        this.id = id;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.items = items;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
