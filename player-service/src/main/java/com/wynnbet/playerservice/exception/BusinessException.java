package com.wynnbet.playerservice.exception;

public abstract class BusinessException extends RuntimeException {
    protected BusinessException(String str) {
        super(str);
    }
}
