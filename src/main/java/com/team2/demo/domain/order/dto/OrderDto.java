package com.team2.demo.domain.order.dto;

import com.team2.demo.domain.order.entity.Order;
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
    private Order.DeliveryStatus deliveryStatus;
    private String buyerEmail;
    private List<ProductItem> items;

    @Getter
    @AllArgsConstructor
    public static class ProductItem {
        private String name;
        private int quantity;
    }

    public OrderDto(Order order) {
        this.orderId = order.getOrderUuid();
        this.orderDate = order.getCreateDate();
        this.totalPrice = order.getTotalAmount();
        this.deliveryStatus = order.getDeliveryStatus();
        this.buyerEmail = order.getUser().getEmail();
        this.items = order.getProducts().stream()
                .map(product -> new ProductItem(product.getProductName(), 1)) // 수량 수동 변경
                .collect(Collectors.toList());
    }
}
