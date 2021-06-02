package server.utility;

import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseCode;
import server.commands.Command;

import java.util.List;

/**
 * Handles requests.
 */
public class RequestHandler {
    private CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Handles requests.
     *
     * @param request Request to be processed.
     * @return Response to request.
     */
    public Response handle(Request request) {
        ResponseCode responseCode = executeCommand(request.getCommandName(), request.getCommandStringArgument(),
                request.getCommandObjectArgument());
        return new Response(responseCode, ResponseOutputDeliver.getAndClear());
    }

    /**
     * Executes a command from a request.
     * @param command               Name of command.
     * @param commandStringArgument String argument for command.
     * @param commandObjectArgument Object argument for command.
     * @return Command execute status.
     */
    private ResponseCode executeCommand(String command, String commandStringArgument,
                                        Object commandObjectArgument) {
        List<Command> commandList = commandManager.getCommands();
        for (int i = 0; i < commandList.size(); i++) {
            String[] nowCommands = commandList.get(i).getName().split(" ");
            String now = nowCommands[0];
            if (now.equals(command) && !(now.equals("help"))) {
                commandList.get(i).execute(commandStringArgument, commandObjectArgument);
                return ResponseCode.OK;
            }
            if (command.equals("execute_script")) {
                if (!commandManager.executeScript(commandStringArgument, commandObjectArgument)) return ResponseCode.OK;
            }
            if (command.equals("help")) {
                commandManager.getAllCommands(commandStringArgument, commandObjectArgument);
                return ResponseCode.OK;
            }
        }
        return ResponseCode.OK;
    }
}