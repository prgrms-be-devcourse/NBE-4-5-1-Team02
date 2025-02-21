package com.team2.demo.domain.product.dto;

import com.team2.demo.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductDto {

    private String productUuid;

    private String productName;

    private String category;

    private Integer productPrice;

    private String productDescription;

    private String imageUrl;

    public static ProductDto of(Product product) {
        return ProductDto.builder()
                .productUuid(product.getProductUuid())
                .productName(product.getProductName())
                .category(product.getCategory())
                .productPrice(product.getProductPrice())
                .productDescription(product.getProductDescription())
                .imageUrl(product.getImageUrl())
                .build();
    }
}
