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

    @NotNull(message = "상품 ID 리스트는 필수입니다.")
    private List<String> productIds;

}
