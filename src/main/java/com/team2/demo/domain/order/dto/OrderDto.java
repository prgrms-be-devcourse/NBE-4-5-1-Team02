package com.team2.demo.domain.order.dto;

import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.entity.Order.DeliveryStatus;
import com.team2.demo.domain.product.entity.Product;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String orderId;
    private LocalDateTime orderDate;
    private Integer totalPrice;
    private DeliveryStatus deliveryStatus;
    private String buyerEmail;
    private List<String> productIds; // product_id 리스트 추가
    private Integer zipCode;

    private String deliveryAddress;

    // 종현 : 반환 타입을 OrderDto로 변경
    public OrderDto (Order order) {
        this.orderId = order.getOrderUuid();
        this.orderDate = order.getCreateDate();
        this.totalPrice = order.getTotalAmount();
        this.deliveryStatus = order.getDeliveryStatus();
        this.buyerEmail = order.getUser().getEmail();
        this.productIds = order.getProducts().stream()
                .map(Product::getProductUuid)
                .collect(Collectors.toList());
    }
}