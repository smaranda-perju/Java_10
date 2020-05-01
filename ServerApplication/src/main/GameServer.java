package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @GameServer
 * defineste portul pentru acces si creaza un socket pentru server caruia ii asigneaza portul.
 * dupa server-ul asteapta un client, accepta conexiunea si executa comenzile date de client printr-un thread.
 */
public class GameServer {
    // Define the port on which the server is listening
    public static final int PORT = 8100;

    public GameServer() throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                // Execute the client's request in a new thread
                new ClientThread(socket).start();
            }
        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        } finally {
            serverSocket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer();
    }

}