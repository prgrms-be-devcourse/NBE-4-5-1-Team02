package com.team2.demo.global.exception.order;

import com.team2.demo.global.exception.ServiceException;

public class NoSuchOrderException extends ServiceException {
    public NoSuchOrderException(String message) {
        super(message);
    }
}
