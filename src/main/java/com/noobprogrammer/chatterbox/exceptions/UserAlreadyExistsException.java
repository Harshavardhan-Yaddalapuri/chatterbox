package com.noobprogrammer.chatterbox.exceptions;

public class UserAlreadyExistsException extends Throwable {
    public UserAlreadyExistsException() {
        super("User is already exists");
    }
}
