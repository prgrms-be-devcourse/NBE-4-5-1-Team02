package com.team2.demo.global.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class RsData<T> {

    private static final RsData

    private String message;
    private T data;

    public static <T> RsData<T> success(T data) {

    }
    public static <T> RsData<T> badRequest(String message) {}
}
