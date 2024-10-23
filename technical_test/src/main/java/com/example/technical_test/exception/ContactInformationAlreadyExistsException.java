package com.example.technical_test.exception;

public class ContactInformationAlreadyExistsException extends RuntimeException{
    public ContactInformationAlreadyExistsException(String value) {
        super(String.format("ContactInformation already exists: %s", value));
    }
}
