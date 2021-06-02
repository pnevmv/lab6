package server.commands;

import common.exceptions.WrongAmountOfElementsException;
import common.interaction.BandRaw;
import common.model.MusicBand;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.IncorrectInputScriptException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputDeliver;

import java.time.LocalDateTime;

/**
 * Command 'remove_lower'. Removes all elements it's lower.
 */
public class RemoveLowerCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager) {
        super("remove_lower {element}", "Remove all items from the collection that are less than the specified one");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String arg, Object objArgument) {
        try {
            if (!arg.isEmpty() || objArgument == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            BandRaw bandRaw = (BandRaw) objArgument;
            MusicBand bandToCompare = new MusicBand(
                    collectionManager.generateNextId(),
                    bandRaw.getName(),
                    bandRaw.getCoordinates(),
                    LocalDateTime.now(),
                    bandRaw.getNumberOfParticipants(),
                    bandRaw.getDescription(),
                    bandRaw.getMusicGenre(),
                    bandRaw.getStudio()
            );
            collectionManager.removeLower(bandToCompare);
            ResponseOutputDeliver.appendLn("Groups successfully deleted!");
            return true;
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputDeliver.appendError("The collection is empty!");
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputDeliver.appendError("Incorrect input!");
        }
        return false;
    }
}
