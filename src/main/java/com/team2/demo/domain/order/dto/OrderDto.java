package com.team2.demo.domain.order.dto;

import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.product.entity.Product;
import com.team2.demo.domain.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long orderUuid;
    private User user;
    private User buyer;
    private List<Product> products;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private Integer totalAmount;
    private String deliveryAddress;
    private Integer zipCode;
    private Order.DeliveryStatus deliveryStatus;

    public static OrderDto of(Order order) {
        return OrderDto.builder()
                .orderUuid(order.getOrderUuid())
                .user(order.getUser())
                .buyer(order.getBuyer())
                .products(order.getProducts())
                .createDate(order.getCreateDate())
                .modifiedDate(order.getModifiedDate())
                .totalAmount(order.getTotalAmount())
                .deliveryAddress(order.getDeliveryAddress())
                .zipCode(order.getZipCode())
                .deliveryStatus(order.getDeliveryStatus())
                .build();
    }
}