package com.example.technical_test.exception;

public class ContactInformationNotFoundByIdException extends RuntimeException {
    public ContactInformationNotFoundByIdException(Integer id) {
        super(String.format("ContactInformation not found by id %d", id));
    }
}
