package it.maram.console.commands;

public class SimpleCommand implements Command {
    String name;
    String description;
    Executable executable;
    boolean requireArgs;
    public SimpleCommand(String name, String description, Executable executable, boolean requiresArgs){
        this.name=name;
        this.description=description;
        this.executable = executable;
        this.requireArgs=requiresArgs;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String... args) {
        executable.execute(args);
    }

    @Override
    public boolean requireArgs() {
        return requireArgs;
    }
}
