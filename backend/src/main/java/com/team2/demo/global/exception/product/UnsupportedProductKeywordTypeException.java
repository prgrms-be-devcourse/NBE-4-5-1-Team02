package com.team2.demo.global.exception.product;

import com.team2.demo.global.exception.ServiceException;

public class UnsupportedProductKeywordTypeException extends ServiceException {
    public UnsupportedProductKeywordTypeException(String s) {
        super(s);
    }
}
