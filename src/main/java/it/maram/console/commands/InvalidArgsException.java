package it.maram.console.commands;

public class InvalidArgsException extends RuntimeException {
    public InvalidArgsException(String message) {
        super(message);
    }
}
