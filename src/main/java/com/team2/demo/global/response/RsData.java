package com.team2.demo.global.response;


import lombok.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RsData<T> {

    private String message;
    private T data;
    private int code;


    public static <T> RsData<T> success(String message, T data) {
        /*RsData<T> rsData = new RsData<>();
        rsData.data = data;
        return rsData;*/

        return new RsData<>(message, data, 200);
    }
    public static <T> RsData<T> badRequest(String message, int code) {
        /*RsData<T> rsData = new RsData<>();
        rsData.message = message;
        return rsData;*/

        return new RsData<>(message, null, code);
    }

}
