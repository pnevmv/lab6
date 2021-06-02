package server.commands;

import common.exceptions.BandCanNotFoundException;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputDeliver;

/**
 * Command 'remove_key'. Removes the element by key.
 */
public class RemoveCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public RemoveCommand(CollectionManager collectionManager) {
        super("remove_key null", "Remove an item from the collection by its key");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String arg, Object objArgument) {
        try {
            if (arg.isEmpty() || objArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Integer key = Integer.parseInt(arg);
            if (collectionManager.getByKey(key) == null) throw new BandCanNotFoundException();
            collectionManager.removeFromCollection(key);
            ResponseOutputDeliver.appendLn("Band successfully deleted!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputDeliver.appendLn("Executing: '" + getName() + "'. Key not entered!");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputDeliver.appendError("The collection is empty!");
        } catch (NumberFormatException exception) {
            ResponseOutputDeliver.appendError("The key must be represented by a number!");
        } catch (BandCanNotFoundException exception) {
            ResponseOutputDeliver.appendError("The group with the given key was not found!");
        }
        return false;
    }
}
