package com.noobprogrammer.chatterbox.exceptions;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String message) {
        super("User not found");
    }
}
