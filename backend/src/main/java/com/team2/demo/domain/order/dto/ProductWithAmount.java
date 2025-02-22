package com.team2.demo.domain.order.dto;

import com.team2.demo.domain.product.entity.Product;

/**
 * 주문 내 물품의 수량과 아이템 엔티티를 저장하는 레코드
 * @param product
 * @param amount
 */
public record ProductWithAmount(Product product, int amount){
}
