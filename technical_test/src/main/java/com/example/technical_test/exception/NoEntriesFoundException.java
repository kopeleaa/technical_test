package com.example.technical_test.exception;

public class NoEntriesFoundException extends RuntimeException{
    public NoEntriesFoundException() {
        super("No entries found.");
    }
}
