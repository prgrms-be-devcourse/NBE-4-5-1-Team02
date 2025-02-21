package com.team2.demo.domain.product.repository;

import com.team2.demo.domain.product.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("한 주문의 모든 상품 가져오기")
    void test1(){
        String orderId = "order-11111-22222-33331";
        Pageable pageable = PageRequest.of(0, 4);

        Page<Product> allByOrderId = productRepository.findAllByOrderId(orderId, pageable);

        Assertions.assertThat(allByOrderId).isNotEmpty();
        Assertions.assertThat(allByOrderId.getTotalElements()).isEqualTo(6);
        Assertions.assertThat(allByOrderId.getTotalPages()).isEqualTo(2);
        Assertions.assertThat(allByOrderId.getNumber()).isEqualTo(0);
        Assertions.assertThat(allByOrderId.getSize()).isEqualTo(4);
    }

}