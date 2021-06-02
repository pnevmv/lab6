package server.commands;

import common.interaction.BandRaw;
import common.model.*;
import common.exceptions.BandCanNotFoundException;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.IncorrectInputScriptException;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputDeliver;

import java.time.LocalDateTime;

/**
 * Command 'update'. Updates the information about selected marine.
 */
public class UpdateCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update id {element}", "Update the value of the collection item whose id is equal to the given");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String arg, Object objectArg) {
        try {
            if (arg.isEmpty() || objectArg == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            Integer id = Integer.parseInt(arg);
            MusicBand oldBand = collectionManager.getById(id);
            if (oldBand == null) throw new BandCanNotFoundException();

            BandRaw bandRaw = (BandRaw) objectArg;
            String name = bandRaw.getName() == null ? oldBand.getName() : bandRaw.getName();
            Coordinates coordinates = bandRaw.getCoordinates() == null ? oldBand.getCoordinates() : bandRaw.getCoordinates();
            LocalDateTime creationDate = oldBand.getCreationDate();
            Long number = bandRaw.getNumberOfParticipants() == -1 ? oldBand.getNumberOfParticipants() : bandRaw.getNumberOfParticipants();
            String description = bandRaw.getDescription() == null ? oldBand.getDescription() : bandRaw.getDescription();
            MusicGenre genre = bandRaw.getMusicGenre() == null ? oldBand.getGenre() : bandRaw.getMusicGenre();
            Studio studio = bandRaw.getStudio() == null ? oldBand.getStudio() : bandRaw.getStudio();

            collectionManager.addToCollection(collectionManager.generateNextId(),
                    new MusicBand(id, name, coordinates, creationDate, number, description, genre, studio));
            collectionManager.removeById(id);
            ResponseOutputDeliver.appendLn("Group updated successfully!");
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputDeliver.appendLn("Executing: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputDeliver.appendError("Collection is empty!");
        } catch (NumberFormatException exception) {
            ResponseOutputDeliver.appendError("ID must be represented by a number!");
        } catch (BandCanNotFoundException exception) {
            ResponseOutputDeliver.appendError("There is no band with this ID in the collection!");
        }
        return false;
    }
}
