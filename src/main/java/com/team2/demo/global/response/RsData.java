package com.team2.demo.global.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RsData<T> {


    private String message;
    private T data;

    public static <T> RsData<T> success(T data) {
        RsData<T> rsData = new RsData<>();
        rsData.message = "Success";
        rsData.data = data;
        return rsData;
    }
    public static <T> RsData<T> badRequest(String message) {
        RsData<T> rsData = new RsData<>();
        rsData.message = message;
        rsData.data = null;
        return rsData;
    }
}
