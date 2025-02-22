package com.team2.demo.global.exception.order;

import com.team2.demo.global.exception.ServiceException;

public class NoProductsInOrderException extends ServiceException {
    public NoProductsInOrderException(String message) {
        super(message);
    }
}
