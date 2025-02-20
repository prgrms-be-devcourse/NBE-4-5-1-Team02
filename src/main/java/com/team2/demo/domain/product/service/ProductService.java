package com.team2.demo.domain.product.service;

import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductDto> getProductList(String keywordType, String keyword, Pageable pageable) {

        KeywordType searchKeywordType = KeywordType.valueOf(keywordType);
        return searchKeywordType.getSearchResult(productRepository, keyword, pageable);
    }
}
