
package com.team2.demo.domain.order.repository;

import com.team2.demo.domain.order.entity.Order;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findAllByBuyer_Email(String email, @ParameterObject Pageable pageable);

    Optional<Order> findByOrderUuid(String orderUuid);

    void deleteByOrderUuid(String orderUuid);

    long countByOrderUuid(String orderUuid);
}
