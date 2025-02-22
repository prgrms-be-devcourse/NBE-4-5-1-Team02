package com.team2.demo.domain.order.dto;

import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.product.entity.Product;
import com.team2.demo.domain.user.dto.UserDto;
import com.team2.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderInfoDto {

    private String orderId;

    private Buyer buyer;

    private List<Product> products = new ArrayList<>();

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    private Integer totalAmount;

    private String address;

    private Integer zipcode;

    private Order.DeliveryStatus deliveryStatus;

    public static OrderInfoDto of(Order response) {
        return OrderInfoDto.builder()
                .orderId(response.getOrderUuid())
                .buyer(new Buyer(response.getBuyer().getEmail()))
                .products(response.getProducts())
                .createAt(response.getCreateDate())
                .modifiedAt(response.getModifiedDate())
                .totalAmount(response.getTotalAmount())
                .address(response.getDeliveryAddress())
                .zipcode(response.getZipCode())
                .deliveryStatus(response.getDeliveryStatus())
                .build();
    }

    @Getter
    @AllArgsConstructor
    static class Buyer{
        private String email;
    }
}
