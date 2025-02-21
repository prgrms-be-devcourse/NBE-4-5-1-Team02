package com.team2.demo.domain.order.dto;

import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.product.entity.Product;
import com.team2.demo.domain.user.dto.UserDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderInfoWithoutItemDto {


    private final String orderUuid;

    private final UserDto user;
    private final LocalDateTime createDate;
    private final LocalDateTime modifiedDate;
    private final Integer totalAmount;
    private final String deliveryAddress;
    private final Integer zipCode;
    private final Order.DeliveryStatus deliveryStatus;

    public OrderInfoWithoutItemDto(Order order){
        this.orderUuid = order.getOrderUuid();
        this.user = UserDto.of(order.getUser());
        this.createDate = order.getCreateDate();
        this.modifiedDate = order.getModifiedDate();
        this.totalAmount = order.getTotalAmount();
        this.deliveryAddress = order.getDeliveryAddress();
        this.zipCode = order.getZipCode();
        this.deliveryStatus = order.getDeliveryStatus();
    }
}
