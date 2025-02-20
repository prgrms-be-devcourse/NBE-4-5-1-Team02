package com.team2.demo.domain.product.controller;

import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.product.service.ProductService;
import com.team2.demo.global.response.PaginationData;
import com.team2.demo.global.response.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    record SearchParams(@NotNull String keywordType, String keyword){
        public SearchParams{
            if(keywordType == null || keywordType.isEmpty())
                keywordType = "title";
            if(keyword == null)
                keyword = "";
        }
    }

    @PostMapping
    public RsData<PaginationData<ProductDto>> getProductList(
            @RequestParam @Valid SearchParams params,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {

        Page<ProductDto> products = productService.getProductList(params.keywordType(), params.keyword(), pageable);
        return RsData.success(
                PaginationData.<ProductDto>builder()
                        .data(products.getContent())
                        .page(products.getNumber())
                        .size(products.getSize())
                        .totalPages(products.getTotalPages())
                        .build()
        );
    }
}
