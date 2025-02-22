package com.team2.demo.domain.order.dto;

import com.team2.demo.domain.product.dto.ProductListDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    @NotBlank(message = "배송 주소를 입력해야 합니다.")
    private String address;

    @NotNull(message = "우편번호를 입력해야 합니다.")
    private Integer zipcode;

    @NotNull(message = "상품 ID 리스트는 필수입니다.")
    private List<ProductListDto> items;
}
