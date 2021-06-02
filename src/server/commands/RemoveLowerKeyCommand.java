package server.commands;

import common.exceptions.CollectionIsEmptyException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputDeliver;

/**
 * Command 'remove_lower_key'. Removes all element if it's key is lower'.
 */
public class RemoveLowerKeyCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public RemoveLowerKeyCommand(CollectionManager collectionManager) {
        super("remove_lower_key null", "Remove from the collection all elements whose key is less than the specified one");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String arg, Object objectArg) {
        try {
            if (arg.isEmpty() || objectArg != null) throw new IllegalArgumentException();

            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            int key = Integer.parseInt(arg);
            collectionManager.removeLowerKey(key);
            ResponseOutputDeliver.appendLn("Bands successfully deleted!");
            return true;
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputDeliver.appendError("Collection is empty!");
        } catch (IllegalArgumentException exc) {
            ResponseOutputDeliver.appendError("Invalid command argument!");
        }
        return false;
    }
}
