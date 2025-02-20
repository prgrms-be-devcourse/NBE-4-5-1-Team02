package com.team2.demo.domain.product.service;

import com.team2.demo.domain.product.controller.ProductController.PaginationData;
import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public enum KeywordType {
    TITLE("title") {
        @Override
        public Page<ProductDto> getSearchResult(ProductRepository productRepository, String keyword, Pageable pageable) {
            return productRepository.findAllByProductNameLike(keyword, pageable)
                    .map(ProductDto::of);
        }
    },
    CATEGORY("category") {
        @Override
        public Page<ProductDto> getSearchResult(ProductRepository productRepository, String keyword, Pageable pageable) {
            return productRepository.findAllByCategoryLike(keyword, pageable)
                    .map(ProductDto::of);

        }
    };
    private final String value;

    public String getValue() {
        return value;
    }

    KeywordType(String value) {
        this.value = value;
    }

    public abstract Page<ProductDto> getSearchResult(ProductRepository productRepository, String keyword, Pageable pageable);

    private static PaginationData<ProductDto> getProductPagination(Page<ProductDto> productPage) {
        return PaginationData.<ProductDto>builder()
                .data(productPage.getContent())
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalPages(productPage.getTotalPages()).build();
    }

    public static KeywordType of(String keywordType) {
        return KeywordType.valueOf(keywordType);
    }
}
