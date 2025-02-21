package com.team2.demo.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.entity.Order.DeliveryStatus;
import com.team2.demo.domain.product.entity.Product;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String orderId;
    private LocalDateTime orderDate;
    private Integer totalPrice;

    private DeliveryStatus deliveryStatus;

    private String buyerEmail;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductItem> items;

    @Getter
    @AllArgsConstructor
    public static class ProductItem {
        private String name;
        private int quantity;
    }

    // 상품 항목을 포함하지 않는 생성자 (사용자 리스트 조회용)
    public OrderDto(Order order) {
        this.orderId = order.getOrderUuid();
        this.orderDate = order.getCreateDate();
        this.totalPrice = order.getTotalAmount();
        this.deliveryStatus = order.getDeliveryStatus();
        this.buyerEmail = order.getUser().getEmail();
    }

    // 종현 : 반환 타입을 OrderDto로 변경
    // 상품 항목을 포함하는 생성자 (관리자 리스트 조회용)
    public OrderDto(Order order, boolean includeItems) {
        this.orderId = order.getOrderUuid();
        this.orderDate = order.getCreateDate();
        this.totalPrice = order.getTotalAmount();
        this.deliveryStatus = order.getDeliveryStatus();
        this.buyerEmail = order.getUser().getEmail();
        if (includeItems) {
            this.items = order.getProducts().stream()
                    .map(product -> new ProductItem(product.getProductName(), 1)) // 수량 수동 변경
                    .collect(Collectors.toList());
        } else {
            this.items = null;  // 상품 항목을 제외
        }
    }
}