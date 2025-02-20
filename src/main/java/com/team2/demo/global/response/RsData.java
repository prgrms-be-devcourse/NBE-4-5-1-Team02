package com.team2.demo.global.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class RsData<T> {

    private String message;
    private T data;
    private int code;

    public static <T> RsData<T> success(String message, T data) {
        return new RsData<>(message, data, HttpStatus.OK.value());
    }
    public static <T> RsData<T> badRequest(String message) {
        return new RsData<>(message,null, HttpStatus.BAD_REQUEST.value());
    }

}
