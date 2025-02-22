package com.team2.demo.domain.product.service;

import com.team2.demo.domain.order.dto.OrderInfoWithoutItemDto;
import com.team2.demo.domain.order.repository.OrderRepository;
import com.team2.demo.domain.order.service.OrderService;
import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.product.repository.ProductRepository;
import com.team2.demo.domain.user.entity.User;
import com.team2.demo.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public Page<ProductDto> getProductList(String keywordType, String keyword, Pageable pageable) {

        ProductKeywordType searchKeywordType = ProductKeywordType.valueOf(keywordType.toUpperCase());
        return searchKeywordType.getSearchResult(productRepository, keyword, pageable);
    }

    public Page<ProductDto> getProductsInOrder(@NotEmpty String orderId, Pageable pageable) {
        return productRepository.findAllByOrderId(orderId, pageable)
                .map(ProductDto::of);
    }

    public Page<ProductDto> getProductsInOrder(@NotEmpty String orderId, String email, Pageable pageable) {

        // 내부에서 조건에 안맞으면 exception throw
        orderService.findOrder(orderId, email);

        return productRepository.findAllByOrderId(orderId, pageable)
                .map(ProductDto::of);
    }
}
