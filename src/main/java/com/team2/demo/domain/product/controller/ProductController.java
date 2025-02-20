package com.team2.demo.domain.product.controller;

import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.product.service.ProductService;
import com.team2.demo.global.response.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public RsData<List<ProductDto>> getProductList(){
        List<ProductDto> products = productService.getProductList();
        return RsData.success(
                "성공했습니다.",
                products
        );
    }
}
