package com.example.technical_test.exception;

public class PersonNotFoundByIdException extends RuntimeException {
    public PersonNotFoundByIdException(Integer id) {
        super(String.format("Person not found by id: %d", id));
    }
}
