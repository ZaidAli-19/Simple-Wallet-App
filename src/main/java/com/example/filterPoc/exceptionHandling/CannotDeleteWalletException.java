package com.example.filterPoc.exceptionHandling;

public class CannotDeleteWalletException extends RuntimeException{
    public CannotDeleteWalletException(String message) {
        super(message);
    }
}
