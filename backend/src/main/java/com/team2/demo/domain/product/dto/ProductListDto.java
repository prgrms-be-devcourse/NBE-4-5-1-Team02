package com.team2.demo.domain.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductListDto {
    @NotBlank(message = "product Id는 필수입니다.")
    private String productId;
    @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
    private int quantity;
}
