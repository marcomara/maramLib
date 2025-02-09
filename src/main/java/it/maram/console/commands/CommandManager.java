package it.maram.console.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();

    public void registerCommand(Command command){
        commands.put(command.getName(), command);
    }
    public void registerCommand(String alias, Command command){
        commands.put(alias, command);
        registerCommand(command);
    }
    public void createCommand(String name, String description, Executable executable, boolean requiresArgs){
        registerCommand(new SimpleCommand(name, description, executable, requiresArgs));
    }
    public void createCommand(String name, String alias ,String description, Executable executable, boolean requiresArgs){
        registerCommand(alias, new SimpleCommand(name, description, executable, requiresArgs));
    }
    public void executeCommand(String name, String... args) throws CommandNotFoundException{
        Command command = commands.get(name);
        if(command == null) throw new CommandNotFoundException(name);
        if(command.requireArgs()){
            if(args.length>0) command.execute(args);
            else System.out.println(command.getName() + " requires arguments!");
        }
        else{
            command.execute();
            if(args.length>0) System.out.println("Arguments are ignored for this command");
        }
    }
    public void listCommands(){
        System.out.println("Available commands:");
        for(String s : commands.keySet()){
            System.out.println("- " + s + ": " + commands.get(s).getDescription());
        }
    }

}
