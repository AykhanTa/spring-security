package com.example.spring_security.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException() {
        super("User already exists");
    }
}
