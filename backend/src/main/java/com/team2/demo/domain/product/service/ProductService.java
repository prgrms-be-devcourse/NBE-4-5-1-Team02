package com.team2.demo.domain.product.service;

import com.team2.demo.domain.order.dto.ProductWithAmount;
import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.repository.OrderRepository;
import com.team2.demo.domain.order.service.OrderService;
import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.product.entity.Product;
import com.team2.demo.domain.product.repository.ProductRepository;
import com.team2.demo.domain.user.entity.User;
import com.team2.demo.domain.user.service.UserService;
import com.team2.demo.global.exception.order.NoSuchOrderException;
import com.team2.demo.global.exception.prodcut.UnsupportedProductKeywordTypeException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserService userService;

    public Page<ProductDto> getProductList(String keywordType, String keyword, Pageable pageable) {

        try{
            ProductKeywordType searchKeywordType = ProductKeywordType.valueOf(keywordType.toUpperCase());
            return searchKeywordType.getSearchResult(productRepository, keyword, pageable);
        }catch (IllegalArgumentException e){
            throw new UnsupportedProductKeywordTypeException("지원하지 않는 검색 키워드 타입입니다.");
        }
    }

    public Page<ProductDto> getPaginatedProductsInOrderAdmin(@NotEmpty String orderId, Pageable pageable) {
        orderService.orderIsExist(orderId);

        return productRepository.findAllByOrderId(orderId, pageable)
                .map(ProductDto::of);
    }

    public Page<ProductDto> getPaginatedProductsInOrder(@NotEmpty String orderId, String email, Pageable pageable) {

        // 내부에서 조건에 안맞으면 exception throw
        orderService.findOrder(orderId, email);


        orderService.orderIsExist(orderId);

        return productRepository.findAllByOrderId(orderId, pageable)
                .map(ProductDto::of);
    }

    public List<ProductWithAmount> getProductsInOrderAdmin(String orderId) {
        Order byOrderUuid = orderRepository.findByOrderUuid(orderId)
                .orElseThrow(() -> new NoSuchOrderException("id가 %s인 order는 없습니다.".formatted(orderId)));
        Map<Product, Integer> amountMap = new HashMap<>();
        byOrderUuid.getProducts().forEach(product -> {
            if (amountMap.containsKey(product)) {
                amountMap.put(product, amountMap.get(product) + 1);
            } else {
                amountMap.put(product, 1);
            }
        });
        return amountMap.entrySet().stream().map(productIntegerEntry ->
                new ProductWithAmount(productIntegerEntry.getKey(), productIntegerEntry.getValue())).toList();
    }

    public List<ProductWithAmount> getProductsInOrder(String orderId, String email) {
        User byEmail = userService.findByEmail(email);
        if(byEmail == null)
            throw new EntityNotFoundException("email이 %s인 유저는 없습니다.".formatted(email));
        return getProductsInOrderAdmin(orderId);
    }
}
