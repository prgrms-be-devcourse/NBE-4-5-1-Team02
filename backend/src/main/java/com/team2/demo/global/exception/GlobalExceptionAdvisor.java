package com.team2.demo.global.exception;

import com.team2.demo.global.response.RsData;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
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
}