package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @run
 * din moment ce clientul poate trimite mai multe comenzi, ele vor fi evaluate intr-un while
 * cu BufferedReader va prelua comenzile clientului care vor fi stocate intr-un string (request)
 * clientul primeste raspunsul cu ajutorul unui PrintWriter
 * daca request-ul este "stop" atunci serverul se va opri, clientul v-a primi un mesaj cum ca sevrerul s-a oprit,
 * bufferul se va goli si se va opri preluarea datelor odata cu inchiderea socket-ului
 * daca request-ul este "exit" atunci doar se va iesi din while pentru ca nu vor mai veni request-uri din partea clientului (serverul nu se opreste in cazul acesta)
 * daca request-ul nu este nici "exit", nici "stop" returneaza  raspunsul clientului si goleste inca o data bufferul
 */
class ClientThread extends Thread {
    private Socket socket = null;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            while (true) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String request = in.readLine();
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                if (request.equals("stop")) {
                    out.println("Server stopped");
                    out.flush();
                    in.close();
                    socket.close();
                    System.exit(0);
                } else if (request.equals("exit")) {
                    break;
                } else {
                    out.println("Server received the request: " + request);
                    out.flush();
                }
            }

        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}