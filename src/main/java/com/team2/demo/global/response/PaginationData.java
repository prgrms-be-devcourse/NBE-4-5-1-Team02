package com.team2.demo.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class PaginationData<T> {
    private List<T> data;
    private int page;
    private int size;
    private int totalPages;
}
