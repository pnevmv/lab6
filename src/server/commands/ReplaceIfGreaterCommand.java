package server.commands;

import common.interaction.BandRaw;
import common.model.MusicBand;
import common.exceptions.BandCanNotFoundException;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputDeliver;

import java.time.LocalDateTime;

/**
 * Command 'replace_if_greater'. Replace element greater than user entered by key.
 */
public class ReplaceIfGreaterCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public ReplaceIfGreaterCommand(CollectionManager collectionManager) {
        super("replace_if_greater null {element}", "Replace value by key if new value is greater than old");
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
            BandRaw bandRaw = (BandRaw) objectArg;
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
            if (collectionManager.getByKey(Integer.parseInt(arg)) == null) throw new BandCanNotFoundException();
            collectionManager.replaceIfGreater(Integer.parseInt(arg), bandToCompare);
            ResponseOutputDeliver.appendLn("Band successfully replaced!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputDeliver.appendLn("Executing: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputDeliver.appendError("Collection is empty!");
        } catch (BandCanNotFoundException exception) {
            ResponseOutputDeliver.appendError("There is no band with such a key");
        }
        return false;
    }
}
