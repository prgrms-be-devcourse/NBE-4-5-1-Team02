package com.team2.demo.domain.order.service;

import com.team2.demo.domain.order.dto.OrderDto;
import com.team2.demo.domain.order.dto.OrderRequestDto;
import com.team2.demo.domain.order.dto.ProductWithAmount;
import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.repository.OrderRepository;
import com.team2.demo.domain.product.entity.Product;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderDto updateOrder(String orderId, OrderRequestDto request) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다: " + orderId));

        Map<Product, Integer> productAmounts = new HashMap<>();

        for(Product product : order.getProducts()){
            if( productAmounts.containsKey(product)) {
                productAmounts.put(product, productAmounts.get(product) + 1);
            }else{
                productAmounts.put(product, 1);
            }
        }

        List<ProductWithAmount> productWithAmounts = productAmounts.entrySet().stream().map(
                entry -> new ProductWithAmount(entry.getKey(), entry.getValue())
        ).toList();

        //주문 정보 업데이트
        order.updateOrder(productWithAmounts, request.getAddress(), request.getZipcode(), order.getDeliveryStatus() );
        return new OrderDto(order);
    }

    // 주문 삭제 기능
    public void deleteOrder(String orderUuid){
        Order order = orderRepository.findByOrderUuid(orderUuid)
                .orElseThrow(()-> new EntityNotFoundException("주문을 찾을 수 없습니다: " + orderUuid));

        orderRepository.delete(order);
    }

}
