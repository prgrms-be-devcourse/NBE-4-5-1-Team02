package com.team2.demo.global.response;


import lombok.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
        /*RsData<T> rsData = new RsData<>();
        rsData.data = data;
        return rsData;*/

        return new RsData<>(message, data, HttpStatus.OK.value());
    }
    public static <T> RsData<T> badRequest(String message) {
        /*RsData<T> rsData = new RsData<>();
        rsData.message = message;
        return rsData;*/

        return new RsData<>(message, null, HttpStatus.BAD_REQUEST.value());
    }

}
