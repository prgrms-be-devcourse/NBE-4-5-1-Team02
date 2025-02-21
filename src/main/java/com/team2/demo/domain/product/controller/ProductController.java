package com.team2.demo.domain.product.controller;

import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.product.service.ProductService;
import com.team2.demo.global.response.PaginationData;
import com.team2.demo.global.response.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
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


    @GetMapping("/products")
    public RsData<PaginationData<ProductDto>> getProductList(
            @ModelAttribute @Valid SearchParams params,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {

        Page<ProductDto> products = productService.getProductList(params.keywordType(), params.keyword(), pageable);
        return RsData.success(
                "Success.",
                PaginationData.<ProductDto>builder()
                        .data(products.getContent())
                        .page(products.getNumber())
                        .size(products.getSize())
                        .totalPages(products.getTotalPages())
                        .build()
        );
    }

    @GetMapping("/admin/orders/{orderId}/products")
    public RsData<PaginationData<ProductDto>> getProductsInOrder(
            @PathVariable(name = "orderId") String orderId,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ){
        Page<ProductDto> items = productService.getProductsInOrder(orderId, pageable);

        PaginationData<ProductDto> products = PaginationData.<ProductDto>builder()
                .data(items.getContent())
                .page(items.getNumber())
                .size(items.getSize())
                .totalPages(items.getTotalPages())
                .build();

        return RsData.success("Success.", products);
    }
}