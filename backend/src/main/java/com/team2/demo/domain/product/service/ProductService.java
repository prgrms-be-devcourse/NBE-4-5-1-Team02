package com.team2.demo.domain.product.service;

import com.team2.demo.domain.order.service.OrderService;
import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.product.repository.ProductRepository;
import com.team2.demo.global.exception.product.UnsupportedProductKeywordTypeException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderService orderService;

    public Page<ProductDto> getProductList(String keywordType, String keyword, Pageable pageable) {

        try {
            ProductKeywordType searchKeywordType = ProductKeywordType.valueOf(keywordType.toUpperCase());
            return searchKeywordType.getSearchResult(productRepository, keyword, pageable);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedProductKeywordTypeException("지원하지 않는 검색 키워드 타입입니다.");
        }
    }

    public Page<ProductDto> getProductsInOrder(@NotEmpty String orderId, Pageable pageable) {

        orderService.orderIsExist(orderId);

        return productRepository.findAllByOrderId(orderId, pageable)
                .map(ProductDto::of);
    }
}
