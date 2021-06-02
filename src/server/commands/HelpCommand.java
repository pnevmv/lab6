package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.ResponseOutputDeliver;

/**
 * Command 'help'. It's here just for logical structure.
 */
public class HelpCommand extends AbstractCommand{

    public HelpCommand() {
        super("help", "Display help for available commands");
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            return true;

        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputDeliver.appendLn("Executing the: '" + getName() + "', but incorrect input");
        }
        return false;
    }
}
