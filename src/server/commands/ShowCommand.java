package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputDeliver;

/**
 * Command 'show'. Shows information about all elements of the collection.
 */
public class ShowCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", "Displaying all elements of the collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArg) {
        try {
            if (!argument.isEmpty() || objectArg != null) throw new WrongAmountOfElementsException();
            ResponseOutputDeliver.appendLn(collectionManager);
            return true;
        } catch (WrongAmountOfElementsException exc) {
            ResponseOutputDeliver.appendLn("Executing the: '" + getName() + "'");
        }
        return false;
    }
}
