package com.team2.demo.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.repository.OrderRepository;
import com.team2.demo.domain.user.dto.UserDto;
import com.team2.demo.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequestDto {
    @NotBlank(message = "배송 주소를 입력해야 합니다.")
    private String deliveryAddress;

    @NotNull(message = "우편번호를 입력해야 합니다.")
    private Integer zipCode;

    @NotNull(message = "상품 ID 리스트는 필수입니다.")
    private List<String> productIds;

    @NotNull(message = "이메일 입력은 필수입니다.")
    private Buyer buyer;
    private LocalDateTime orderDate;
    private Integer totalAmount;
    private Order.DeliveryStatus deliveryStatus;
    private User user;

    // 엔티티 -> DTO 변환
    public static OrderRequestDto of(Order order) {

        return OrderRequestDto.builder()
                .orderDate(order.getCreateDate())
                .user(order.getUser())
                .zipCode(order.getZipCode())
                .deliveryAddress(order.getDeliveryAddress())
                .zipCode(order.getZipCode())
                .totalAmount(order.getTotalAmount())
                .deliveryStatus(order.getDeliveryStatus())
//                .orders(user.getOrders().stream()
//                        .map(OrderDto::of) // Order 엔티티 -> OrderDto 변환
//                        .collect(Collectors.toList()))
                .build();

    }

    @Getter
    public class Buyer{
        private String email;
    }
}
