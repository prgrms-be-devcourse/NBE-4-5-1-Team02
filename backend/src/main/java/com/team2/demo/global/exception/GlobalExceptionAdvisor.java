package com.team2.demo.global.exception;

import com.team2.demo.global.response.RsData;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvisor {

    @ExceptionHandler(ServiceException.class)
    public RsData<Void> serviceException(ServiceException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        e.printStackTrace();
        return RsData.badRequest(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public RsData<Void> handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        e.printStackTrace();
        return RsData.badRequest("주문이 없습니다.", HttpStatus.BAD_REQUEST.value());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RsData<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        e.printStackTrace();
        return RsData.badRequest("올바른 이메일 형식이어야 합니다.", HttpStatus.BAD_REQUEST.value());
    }
}