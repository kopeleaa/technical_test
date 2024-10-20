package com.example.technical_test.exception.handler;

public class NoEntriesFoundException extends RuntimeException{
    public NoEntriesFoundException() {
        super("No entries found.");
    }
}
