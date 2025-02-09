package it.maram.console.commands;

public interface Command {
    String getName();
    String getDescription();
    void execute(String... args) throws InvalidArgsException;
    boolean requireArgs();
}
