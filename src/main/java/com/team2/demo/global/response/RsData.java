package com.team2.demo.global.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class RsData<T> {

    private int resultCode;
    private String message;
    private T data;

    public RsData(int resultCode, String message, T data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

    public static <T> RsData<T> success(T data) {
       return new RsData<>(HttpStatus.OK.value(), "성공", data);
    }

    public static <T> RsData<T> fail(String message) {
        return new RsData<>(HttpStatus.BAD_REQUEST.value(), message, null);
    }
}
