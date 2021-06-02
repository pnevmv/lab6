package server;

import server.utility.*;
import server.commands.*;

/**
 * Main server class. Creates all server instances.
 * @author Smirnov Danil.
 */
public class ServerApp {
    public static final int PORT = 1488;
    public static final int CONNECTION_TIMEOUT = 2 * 60 * 1000;

    public static void main(String[] args) {
        FileManager fileManager = new FileManager(args.length == 0?"":args[0]);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new InsertCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveCommand(collectionManager),
                new ClearCommand(collectionManager),
                new ExecuteScriptCommand(),
                new ExitCommand(collectionManager),
                new RemoveLowerCommand(collectionManager),
                new ReplaceIfGreaterCommand(collectionManager),
                new RemoveLowerKeyCommand(collectionManager),
                new AverageOfNumberParticipantsCommand(collectionManager),
                new FilterCommand(collectionManager),
                new FieldsOfDescriptionsCommand(collectionManager));
        RequestHandler requestHandler = new RequestHandler(commandManager);
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler);
        server.run();
    }
}
