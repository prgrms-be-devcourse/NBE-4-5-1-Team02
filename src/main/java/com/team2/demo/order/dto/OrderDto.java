package com.team2.demo.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDto {
    private String deliveryAddress;
    private Integer zipCode;
    private String deliveryStatus;
    private Integer totalAmount;
}
