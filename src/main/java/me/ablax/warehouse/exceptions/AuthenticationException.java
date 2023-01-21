package me.ablax.warehouse.exceptions;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(final String message) {
        super(message);
    }
}
