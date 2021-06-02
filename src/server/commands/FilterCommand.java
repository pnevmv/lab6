package server.commands;

import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputDeliver;

/**
 * Command 'filter_starts_with description'. Finds element with description start.
 */
public class FilterCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public FilterCommand(CollectionManager collectionManager) {
        super("filter_starts_with description", "Display elements whose description field value begins with a given substring");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            String filteredInfo = collectionManager.descriptionFilter(stringArgument);
            if (!filteredInfo.isEmpty()) {
                ResponseOutputDeliver.appendLn(filteredInfo);
                return true;
            } else  ResponseOutputDeliver.appendLn("There are no groups in the collection with a suitable description!");
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputDeliver.appendLn("Executing the: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputDeliver.appendError("Collection is empty!");
        } catch (IllegalArgumentException exception) {
            ResponseOutputDeliver.appendError("Description isn't in collection!");
        }
        return false;
    }
}
