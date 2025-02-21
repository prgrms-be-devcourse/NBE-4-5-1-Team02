package com.team2.demo.domain.order.dto;

import com.team2.demo.domain.product.entity.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderItemGrouper {
    public static Map<String, Integer> countProducts(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getProductName, Collectors.summingInt(p -> 1))); // 개수 계산
    }
}
