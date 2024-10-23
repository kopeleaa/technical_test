package com.example.technical_test.exception;

public class AddressAlreadyInUseException extends RuntimeException{
    public AddressAlreadyInUseException(Integer addressId) {
        super(String.format("Address already in use: %s", addressId));
    }
}
