package com.team2.demo.global.response;

import com.team2.demo.domain.order.dto.OrderDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderListResponse {
    private List<OrderDto> content;
    private int page;
    private int size;
    private int totalPages;
}
