package com.team2.demo.order.repository;

import com.team2.demo.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByOrderUuid(String orderUuid);
}
