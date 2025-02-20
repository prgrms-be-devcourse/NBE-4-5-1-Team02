package com.team2.demo.domain.order.repository;

import com.team2.demo.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findById(String orderUuid);
}
