package com.team2.demo.domain.product.entity; // Order 와 다른 패키지에 위치

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "PRODUCT")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "PRODUCT_UUID")
    private String productUuid;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "PRODUCT_PRICE")
    private Integer productPrice;

    @Column(name = "PRODUCT_DESCRIPTION")
    private String productDescription;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product product)) return false;
        return Objects.equals(productUuid, product.productUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productUuid);
    }
}