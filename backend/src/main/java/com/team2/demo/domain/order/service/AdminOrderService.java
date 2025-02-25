package com.team2.demo.domain.order.service;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.dto.OrderRequestDto;
import com.team2.demo.domain.order.dto.ProductWithAmount;
import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.repository.OrderRepository;
import com.team2.demo.domain.product.dto.ProductListDto;
import com.team2.demo.domain.product.repository.ProductRepository;
import com.team2.demo.domain.product.service.ProductService;
import com.team2.demo.global.exception.product.NoSuchProductException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Transactional
    public OrderDto updateOrder(String orderId, OrderRequestDto request) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다: " + orderId));

        Map<String, Integer> productAmounts = new HashMap<>();

        for(ProductListDto product : request.getItems())
            productAmounts.put(product.getProductId(), product.getQuantity());

        List<ProductWithAmount> productWithAmounts = productAmounts.entrySet().stream().map(
                entry -> new ProductWithAmount(
                        productRepository.findByProductUuid(
                                entry.getKey())
                                .orElseThrow(()->new NoSuchProductException("id가 %s인 product는 없습니다."
                                        .formatted(entry.getKey()))),
                        entry.getValue())
        ).toList();

        //주문 정보 업데이트
        order.updateOrder(productWithAmounts, request.getAddress(), request.getZipcode(), request.getDeliveryStatus() );
        return new OrderDto(order);
    }

    // 주문 삭제 기능
    public void deleteOrder(String orderUuid){
        Order order = orderRepository.findByOrderUuid(orderUuid)
                .orElseThrow(()-> new EntityNotFoundException("주문을 찾을 수 없습니다: " + orderUuid));

        orderRepository.delete(order);
    }

}
