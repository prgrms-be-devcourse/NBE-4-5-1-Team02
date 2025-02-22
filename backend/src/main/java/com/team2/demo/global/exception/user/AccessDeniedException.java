package com.team2.demo.global.exception.user;

import com.team2.demo.global.exception.ServiceException;

public class AccessDeniedException extends ServiceException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
