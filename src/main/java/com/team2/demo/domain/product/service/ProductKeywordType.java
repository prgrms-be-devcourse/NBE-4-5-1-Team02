package com.team2.demo.domain.product.service;

import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.product.repository.ProductRepository;
import com.team2.demo.global.response.PaginationData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Product의 KeywordType을 나타내는 enum. <br/>
 */
public enum ProductKeywordType {
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
    private final String typeName;

    public String getTypeName() {
        return typeName;
    }

    ProductKeywordType(String typeName) {
        this.typeName = typeName;
    }

    /**
     * keyword type에 따라 {@link ProductRepository}의 메소드를 호출한다.
     * @param productRepository {@link com.team2.demo.domain.product.entity.Product} 를 조회할 리포지토리
     * @param keyword 검색할 keyword값
     * @param pageable {@link Pageable}
     * @return {@link Page<ProductDto>}
     */
    public abstract Page<ProductDto> getSearchResult(ProductRepository productRepository, String keyword, Pageable pageable);


    public static ProductKeywordType of(String keywordType) {
        return ProductKeywordType.valueOf(keywordType);
    }
}
