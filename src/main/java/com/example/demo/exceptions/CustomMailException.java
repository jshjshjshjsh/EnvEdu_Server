package com.example.demo.exceptions;

public class CustomMailException extends RuntimeException{
    public CustomMailException(String message) {
        super(message);
    }
}
