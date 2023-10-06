package com.example.demo.exceptions;

public class AstronautNotFoundException extends RuntimeException {
    public AstronautNotFoundException(String message) {
        super(message);
    }
}
