package com.tree.springcloud.bl.service.exception;

public class AlreadyExistsException extends Exception {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
