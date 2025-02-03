package com.ps.back.model.exception;

public class JsonNullException extends RuntimeException {
    public JsonNullException(String message) {
        super(message);
    }
}
