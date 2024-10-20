package com.example.technical_test.exception;

public class PersonAlreadyExistsException extends RuntimeException {

    public PersonAlreadyExistsException(String name) {
        super(String.format("Person %s already exists", name));
    }
}
