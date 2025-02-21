package com.team2.demo.domain.order.dto;

import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderInfoDtoAdmin {
    private String orderId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto buyer;
    private String address;
    private Integer zipCode;
    private Order.DeliveryStatus deliveryStatus;
    private Integer totalPrice;
    private List<ProductDto> items;
}
