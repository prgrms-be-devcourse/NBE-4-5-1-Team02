package com.team2.demo.domain.product.controller;

import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.product.service.ProductService;
import com.team2.demo.global.response.PaginationData;
import com.team2.demo.global.response.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping
    public RsData<PaginationData<ProductDto>> getProductList(Pageable pageable){
        Page<ProductDto> products = productService.getProductList(pageable);
        return RsData.success(
                "성공했습니다.",
                        PaginationData.<ProductDto>builder()
                        .data(products.getContent())
                        .page(products.getNumber())
                        .size(products.getSize())
                        .totalPages(products.getTotalPages())
                        .build()
        );
    }
}
