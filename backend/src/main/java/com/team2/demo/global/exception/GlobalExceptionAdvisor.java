package com.team2.demo.global.exception;

import com.team2.demo.global.response.RsData;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvisor {


    @ExceptionHandler(ServiceException.class)
    public RsData<Void> handleServiceException(ServiceException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return RsData.badRequest(e);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RsData<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return RsData.badRequest(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RsData<Void> handleMethodArgumentNotValidException
            (MethodArgumentNotValidException e, HttpServletResponse response, BindingResult bindingResult) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        e.printStackTrace();
        StringBuilder builder = new StringBuilder();
        bindingResult.getFieldErrors().stream().forEach(item ->  builder.append(item.getDefaultMessage()));
        return RsData.badRequest(builder.toString(), HttpStatus.BAD_REQUEST.value());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public RsData<Void> handleConstraintViolationException
            (ConstraintViolationException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        e.printStackTrace();
        return RsData.badRequest(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public RsData<Void> handleIllegalArgumentException
            (IllegalArgumentException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        e.printStackTrace();
        return RsData.badRequest(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
}

