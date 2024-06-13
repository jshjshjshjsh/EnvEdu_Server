package com.example.demo.jpa.exceptions;

public class CustomMailException extends RuntimeException{
    public CustomMailException(String message) {
        super(message);
    }
}
