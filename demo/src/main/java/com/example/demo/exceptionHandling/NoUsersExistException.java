package com.example.demo.exceptionHandling;

public class NoUsersExistException extends RuntimeException {

    public NoUsersExistException(String message) {
        super(message);
    }
}
