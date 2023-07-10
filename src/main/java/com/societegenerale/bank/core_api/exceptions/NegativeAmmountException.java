package com.societegenerale.bank.core_api.exceptions;

public class NegativeAmmountException extends RuntimeException {
    public NegativeAmmountException(String message) {
        super(message);
    }
}
