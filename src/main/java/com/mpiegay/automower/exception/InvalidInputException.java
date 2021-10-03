package com.mpiegay.automower.exception;

/**
 * Exception thrown when input data is invalid
 */
public class InvalidInputException extends IllegalArgumentException {
    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String invalidInput, String explanation, String object) {
        this(String.format("Invalid input string '%s' for %s. ", invalidInput, object) + explanation);
    }
}
