package server.commands;

import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputDeliver;

/**
 * Command 'AverageOfNumberOfParticipants'. Return average of number of participants in whole collection.
 */
public class AverageOfNumberParticipantsCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public AverageOfNumberParticipantsCommand(CollectionManager collectionManager) {
        super("average_of_number_of_participant", "Print the average of the numberOfParticipants field for all elements in the collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            double avNum = collectionManager.getAverageOfNumberOfParticipants();
            if (avNum == 0) throw new CollectionIsEmptyException();
            ResponseOutputDeliver.appendLn("Average of number of participants: " + avNum);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputDeliver.appendLn("Executing the: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputDeliver.appendError("Collection is empty!");
        }
        return false;
    }
}
