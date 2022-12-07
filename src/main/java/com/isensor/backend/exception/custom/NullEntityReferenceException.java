package com.isensor.backend.exception.custom;

public class NullEntityReferenceException extends RuntimeException{

    public NullEntityReferenceException() {    }

    public NullEntityReferenceException(String message) {
        super(message);
    }
}
