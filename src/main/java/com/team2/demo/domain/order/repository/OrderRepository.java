package com.team2.demo.domain.order.repository;

import com.team2.demo.domain.order.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findAllByUser_Email(String email, Pageable pageable);
    // 종현 : 사용자 이메일로 주문 목록 조회
}
