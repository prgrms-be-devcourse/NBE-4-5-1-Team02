package com.team2.demo.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.user.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String id;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<OrderDto> orders;
    @Valid
    private Buyer buyer;

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

    @Getter
    public class Buyer{
        @NotNull(message = "이메일은 필수 값 입니다.")
        @Email(message = "올바른 이메일 형식을 작성해주세요.")
        private String email;
    }

}
