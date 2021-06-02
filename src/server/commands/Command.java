package server.commands;

/**
 * Interface for all commands.
 */
public interface Command {
    String getDescription();
    String getName();
    boolean execute(String stringArgument, Object objectArgument);
}