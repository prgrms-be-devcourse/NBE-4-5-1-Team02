package com.team2.demo.domain.user.dto;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class UserDto {
    private String id;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<OrderDto> orders;

    // 엔티티 -> DTO 변환
    public static UserDto of(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .createdDate(user.getCreatedDate())
                .modifiedDate(user.getModifiedDate())
//                .orders(user.getOrders().stream()
//                        .map(OrderDto::of) // Order 엔티티 -> OrderDto 변환
//                        .collect(Collectors.toList()))
                .build();
    }
}
