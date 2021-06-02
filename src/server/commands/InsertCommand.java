package server.commands;

import common.interaction.BandRaw;
import common.model.MusicBand;
import common.exceptions.WrongAmountOfElementsException;
import common.utility.OutputDeliver;
import server.utility.CollectionManager;
import server.utility.ResponseOutputDeliver;

import java.time.LocalDateTime;

/**
 * Command 'insert key'. Adds new element with key.
 */
public class InsertCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public InsertCommand(CollectionManager collectionManager) {
        super("insert <key> {element}", "Add a new item with the given key");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            Integer IdAndKey = collectionManager.generateNextId();
            BandRaw bandRaw = (BandRaw) objectArgument;
            collectionManager.addToCollection(Integer.parseInt(stringArgument),
                    new MusicBand(IdAndKey, bandRaw.getName(), bandRaw.getCoordinates(),
                            LocalDateTime.now(), bandRaw.getNumberOfParticipants(),
                            bandRaw.getDescription(), bandRaw.getMusicGenre(),
                            bandRaw.getStudio()));
            ResponseOutputDeliver.appendLn("Band added successfully!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputDeliver.appendError("The command was entered in the wrong format!");
        }
        return false;
    }
}
