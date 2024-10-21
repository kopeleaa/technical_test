package com.example.technical_test.exception;

public class AddressAlreadyExistsException extends RuntimeException{
    public AddressAlreadyExistsException(String city) {
        super(String.format("Address already exists %s", city));
    }
}
