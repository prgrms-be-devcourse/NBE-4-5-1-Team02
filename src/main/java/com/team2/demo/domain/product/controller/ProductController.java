package com.team2.demo.domain.product.controller;

import com.team2.demo.global.response.RsData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @PostMapping
    public RsData<List<ProductDto>> getProductList(){
        return new RsData<>()
    }
}
