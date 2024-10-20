package com.example.technical_test.exception;

public class AddressNotFoundByIdException extends RuntimeException{
    public AddressNotFoundByIdException(Integer id) {
        super(String.format("Address not found by id %d", id));
    }
}
