package com.curso.pedidos.repository;

import com.curso.pedidos.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerEmail(String email);

    @Query("SELECT DISTINCT o FROM Order o " +
           "JOIN o.items i " +
           "JOIN i.product p " +
           "WHERE p.category.name = :categoryName")
    List<Order> findOrdersByProductCategory(@Param("categoryName") String categoryName);
}
