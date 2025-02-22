package com.team2.demo.domain.product.controller;

import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.product.service.ProductService;
import com.team2.demo.global.response.PaginationData;
import com.team2.demo.global.response.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public RsData<PaginationData<ProductDto>> getProductList(
            @RequestParam(name="keyword-type", defaultValue = "title") String keywordType,
            @RequestParam(name="keyword", defaultValue = "") String keyword,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {

        Page<ProductDto> products = productService.getProductList(keywordType, keyword, pageable);
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
