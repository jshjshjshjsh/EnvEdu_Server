package com.example.demo.exceptions;

import lombok.Getter;

public class DuplicateAttributeException extends RuntimeException {
    private final String attribute;
    public DuplicateAttributeException(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
