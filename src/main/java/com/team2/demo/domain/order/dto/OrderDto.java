package com.team2.demo.domain.order.dto;

import com.team2.demo.domain.order.entity.Order;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String orderId;
    private LocalDateTime orderDate;
    private Integer totalPrice;
    private String deliveryStatus;
    private String buyerEmail;

    // 종현 : 반환 타입을 OrderDto로 변경
    public OrderDto (Order order) {
        this.orderId = order.getOrderUuid();
        this.orderDate = order.getCreateDate();
        this.totalPrice = order.getTotalAmount();
        this.deliveryStatus = order.getDeliveryStatus();
        this.buyerEmail = order.getUser().getEmail();
    }
}
