package com.team2.demo.global.exception.prodcut;

import com.team2.demo.global.exception.ServiceException;

public class UnsupportedProductKeywordTypeException extends ServiceException {
    public UnsupportedProductKeywordTypeException(String message) {
        super(message);
    }
}
