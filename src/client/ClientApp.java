package client;

import common.exceptions.DeclaredLimitException;
import common.exceptions.WrongAmountOfElementsException;
import common.utility.OutputDeliver;

import java.util.Scanner;

/**
 * Main client class. Creates all client instances.
 * @author Smirnov Danil.
 */
public class ClientApp {
    public static final String PS1 = ">>> ";
    public static final String PS2 = "=>> ";

    private static final int MAX_RECONNECTION_ATTEMPTS = 5;
    private static final int RECONNECTION_TIMEOUT = 5 * 1000;

    private static String host;
    private static int port;

    private static boolean initializeConnectionAddress(String host1, int port1) {
        try {
            host = host1;
            port = port1;
            if (host.isEmpty() || port == 0) throw new WrongAmountOfElementsException();
            if (port < 0) throw new DeclaredLimitException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            String jarName = new java.io.File(ClientApp.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            OutputDeliver.println("Использование: 'java -jar " + jarName + " <host> <port>'");
        } catch (NumberFormatException exception) {
            OutputDeliver.printError("Порт должен быть представлен числом!");
        } catch (DeclaredLimitException exception) {
            OutputDeliver.printError("Порт не может быть отрицательным!");
        }
        return false;
    }
    public static void main(String[] args) {
        String host1 = args[0];
        int port1 = Integer.parseInt(args[1]);
        if (args.length == 0) {
            host1 = "127.0.0.1";
            port1 = 1488;
        }
        if (!initializeConnectionAddress(host1, port1)) return;
        Scanner userScanner = new Scanner(System.in);
        UserHandler userHandler = new UserHandler(userScanner);
        Client client = new Client(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS, userHandler);
        client.run();
        userScanner.close();
    }
}
