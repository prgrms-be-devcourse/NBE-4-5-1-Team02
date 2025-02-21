package com.team2.demo.domain.product.service;

import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.product.repository.ProductRepository;
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

    public Page<ProductDto> getProductList(String keywordType, String keyword, Pageable pageable) {

        ProductKeywordType searchKeywordType = ProductKeywordType.valueOf(keywordType.toUpperCase());
        return searchKeywordType.getSearchResult(productRepository, keyword, pageable);
    }

    public Page<ProductDto> getProductsInOrder(@NotEmpty String orderId, Pageable pageable) {
        return productRepository.findAllByOrderId(orderId, pageable)
                .map(ProductDto::of);
    }
}
