package server.commands;

import common.exceptions.CollectionIsEmptyException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputDeliver;

/**
 * Command 'print_fields_ascending_description'. Prints all descriptions.
 */
public class FieldsOfDescriptionsCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public FieldsOfDescriptionsCommand(CollectionManager collectionManager) {
        super("print_field_ascending_description", "Print the values of the description field of all elements in ascending order");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String StringArgument, Object objectArgument) {
        try {
            if (!StringArgument.isEmpty() || objectArgument != null) throw new IllegalArgumentException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            String filteredInfo = collectionManager.getAllDescriptions();
            if (!filteredInfo.isEmpty()) {
                ResponseOutputDeliver.appendLn(filteredInfo);
                return true;
            } else  ResponseOutputDeliver.appendLn("There are no bands in the collection with such a description.!");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputDeliver.appendError("Collection is empty!");
        } catch (IllegalArgumentException exception) {
            ResponseOutputDeliver.appendError("Incorrect input!");
        }
        return false;
    }
}
