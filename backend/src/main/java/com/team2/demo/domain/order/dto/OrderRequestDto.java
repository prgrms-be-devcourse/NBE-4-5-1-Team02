package com.team2.demo.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    @NotBlank(message = "배송 주소를 입력해야 합니다.")
    private String deliveryAddress;

    @NotNull(message = "우편번호를 입력해야 합니다.")
    private Integer zipCode;

    private List<String> productIds;

}
