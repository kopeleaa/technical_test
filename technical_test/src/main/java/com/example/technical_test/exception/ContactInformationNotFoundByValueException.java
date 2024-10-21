package com.example.technical_test.exception;

public class ContactInformationNotFoundByValueException extends RuntimeException {
    public ContactInformationNotFoundByValueException(String value) {
        super(String.format("Contact information not found by value %s", value));
    }
}
