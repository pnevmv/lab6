package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputDeliver;

/**
 * Command 'exit'. Checks for wrong arguments then finishing app.
 */
public class ExitCommand extends AbstractCommand{
CollectionManager collectionManager;

    public ExitCommand(CollectionManager collectionManager) {
        super("exit", "Exit of client application");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArg, Object objectArg) {
        try {
            if (!stringArg.isEmpty() || objectArg != null) throw new WrongAmountOfElementsException();
            collectionManager.saveCollection();
            ResponseOutputDeliver.appendLn("Collection saving finished");
            ResponseOutputDeliver.appendLn("Closing the client application...");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputDeliver.appendLn("Executing the: '" + getName() + "'");
        }
        return false;
    }
}
