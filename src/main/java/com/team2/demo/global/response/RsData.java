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
@AllArgsConstructor
public class RsData<T> {

    private String message;
    private T data;
    private int code;


    public static <T> RsData<T> success(T data) {
        /*RsData<T> rsData = new RsData<>();
        rsData.data = data;
        return rsData;*/

        return new RsData<>("Success", data);
    }
    public static <T> RsData<T> badRequest(String message) {
        /*RsData<T> rsData = new RsData<>();
        rsData.message = message;
        return rsData;*/

        return new RsData<>(message, null);
    }

}
