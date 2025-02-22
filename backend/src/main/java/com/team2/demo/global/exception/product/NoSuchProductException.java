package com.team2.demo.global.exception.product;

import com.team2.demo.global.exception.ServiceException;

public class NoSuchProductException extends ServiceException {
    public NoSuchProductException(String s) {
        super(s);
    }
}
