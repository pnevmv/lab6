package client;

import common.exceptions.CommandUsageException;
import common.exceptions.IncorrectInputScriptException;
import common.exceptions.ScriptRecursionException;
import common.interaction.*;
import common.model.*;
import common.utility.OutputDeliver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Receives user requests.
 */

public class UserHandler {
    private final int maxRewriteAttempts = 1;

    private Scanner userScanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public UserHandler(Scanner userScanner) {
        this.userScanner = userScanner;
    }
    /**
     * Receives user input.
     * @param serverResponseCode Last server's response code.
     * @return New request to server.
     */
    public Request handle(ResponseCode serverResponseCode) {
        String userInput;
        String[] userCommand;
        ProcessingCode processingCode;
        int rewriteAttempts = 0;
        try {
            do {
                try {
                    if (fileMode() && (serverResponseCode == ResponseCode.ERROR ||
                            serverResponseCode == ResponseCode.SERVER_EXIT))
                        throw new IncorrectInputScriptException();
                    while (fileMode() && !userScanner.hasNextLine()) {
                        userScanner.close();
                        userScanner = scannerStack.pop();
                        OutputDeliver.println("Back to the script '" + scriptStack.pop().getName() + "'...");
                    }
                    if (fileMode()) {
                        userInput = userScanner.nextLine();
                        if (!userInput.isEmpty()) {
                            OutputDeliver.print(ClientApp.PS1);
                            OutputDeliver.println(userInput);
                        }
                    } else {
                        OutputDeliver.print(ClientApp.PS1);
                        userInput = userScanner.nextLine();
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    OutputDeliver.println();
                    OutputDeliver.printError("An error occurred while entering the command!");
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                        OutputDeliver.printError("Number of input attempts exceeded!");
                        System.exit(0);
                    }
                }
                processingCode = processCommand(userCommand[0], userCommand[1]);
            } while (processingCode == ProcessingCode.ERROR && !fileMode() || userCommand[0].isEmpty());
            try {
                if (fileMode() && (serverResponseCode == ResponseCode.ERROR || processingCode == ProcessingCode.ERROR))
                    throw new IncorrectInputScriptException();
                switch (processingCode) {
                    case OBJECT:
                        BandRaw bandAddRaw = generateBandAdd();
                        return new Request(userCommand[0], userCommand[1], bandAddRaw);
                    case UPDATE_OBJECT:
                        BandRaw bandUpdateRaw = generateBandUpdate();
                        return new Request(userCommand[0], userCommand[1], bandUpdateRaw);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursionException();
                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        OutputDeliver.println("Executing the script '" + scriptFile.getName() + "'...");
                        break;
                }
            } catch (FileNotFoundException exception) {
                OutputDeliver.printError("Script file not found!");
            } catch (ScriptRecursionException exception) {
                OutputDeliver.printError("Scripts cannot be called recursively!");
                throw new IncorrectInputScriptException();
            }
        } catch (IncorrectInputScriptException exception) {
            OutputDeliver.printError("The script was interrupted!");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return new Request();
        }
        return new Request(userCommand[0], userCommand[1]);
    }


    /**
     * Processes the entered command.
     *
     * @return Status of code.
     */
    private ProcessingCode processCommand(String command, String commandArgument) {
        try {
            switch (command) {
                case "":
                    return ProcessingCode.ERROR;
                case "help":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("(use just a help)");
                    break;
                case "info":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("(use just an info)");
                    break;
                case "show":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("(use just a show)");
                    break;
                case "insert":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<key>");
                    return ProcessingCode.OBJECT;
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID>");
                    return ProcessingCode.UPDATE_OBJECT;
                case "remove_key":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<key>");
                    break;
                case "clear":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("(about key)");
                    break;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<file_name>");
                    return ProcessingCode.SCRIPT;
                case "exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("(use just an exit)");
                    break;
                case "remove_lower":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "replace_if_greater":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<key> {element}");
                    return ProcessingCode.OBJECT;
                case "remove_lower_key":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<key>'");
                    break;
                case "average_of_number_of_participant":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("(use just a command)");
                    break;
                case "filter_starts_with":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<description>");
                    break;
                case "print_field_ascending_description":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "server_exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("just a 'server_exit'");
                    break;
                default:
                    OutputDeliver.println("Command '" + command + "' not found. Enter 'help'.");
                    return ProcessingCode.ERROR;
            }
        } catch (CommandUsageException exception) {
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            OutputDeliver.println("Использование: '" + command + "'");
            return ProcessingCode.ERROR;
        }
        return ProcessingCode.OK;
    }


    /**
     * Generates band to add.
     * @return Band to add.
     * @throws IncorrectInputScriptException When something went wrong in script.
     */
    private BandRaw generateBandAdd() throws IncorrectInputScriptException {
        BandBuilder bandBuilder = new BandBuilder(userScanner);
        if (fileMode()) bandBuilder.setFileMode();
        return new BandRaw(
                bandBuilder.askName(),
                bandBuilder.askCoordinates(),
                bandBuilder.askNumberOfParticipant(),
                bandBuilder.askDescription(),
                bandBuilder.askGenre(),
                bandBuilder.askStudio()
        );
    }

    /**
     * Generates band to update.
     * @return Band to update.
     * @throws IncorrectInputScriptException When something went wrong in script.
     */
    private BandRaw generateBandUpdate() throws IncorrectInputScriptException {
        BandBuilder bandBuilder = new BandBuilder(userScanner);
        if (fileMode()) bandBuilder.setFileMode();
        String name = bandBuilder.askQuestion("Do you want to change the name?") ?
                bandBuilder.askName() : null;
        Coordinates coordinates = bandBuilder.askQuestion("Do you want to change the coordinates?") ?
                bandBuilder.askCoordinates() : null;
        Long numberOfParticipants = bandBuilder.askQuestion("Do you want to change number of participants?") ?
                bandBuilder.askNumberOfParticipant() : -1;
        String description = bandBuilder.askQuestion("Do you want to change the description?") ?
                bandBuilder.askDescription() : null;
        MusicGenre genre = bandBuilder.askQuestion("Do you want to change music genre?") ?
                bandBuilder.askGenre() : null;
        Studio studio = bandBuilder.askQuestion("Do you want to change the studio?") ?
                bandBuilder.askStudio() : null;
        return new BandRaw(
                name,
                coordinates,
                numberOfParticipants,
                description,
                genre,
                studio
        );
    }

    /**
     * Checks if UserHandler is in file mode now.
     * @return Is UserHandler in file mode now boolean.
     */
    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}
