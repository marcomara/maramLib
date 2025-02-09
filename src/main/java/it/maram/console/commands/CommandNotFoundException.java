package it.maram.console.commands;

public class CommandNotFoundException extends RuntimeException {
    public CommandNotFoundException(String message) {
        super("The command \"" + message + "\" does not exists");
    }
}
