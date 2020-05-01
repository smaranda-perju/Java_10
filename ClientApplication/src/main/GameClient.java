package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 * @main
 * am creat un scanner pentru a putea prelua comenzile de la tastatura
 * am definit adresa si port-ul server-ului la care are loc conexiunea si am cream un socket pe baza acestor date
 * am definit "in" si "out" pentru comunicarea catre server
 * cu ajutorul buclei while clientul va putea face mai multe request-uri catre server fiind doua cazuri in care bucla se opreste:
 * daca request-ul transmis catre server este "exit"
 * sau daca request-ul este "stop" atunci serverul se va opri si va transmite un anumit mesaj clientului ("Server stopped")
 *
 * daca niciunul dintre aceste doua mesaje nu sunt transmise catre server atunci bucla va continua si clientul va putea
 * face request-uri catre server in continuare
 */
public class GameClient {
    public static void main (String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String serverAddress = "127.0.0.1"; 
        int PORT = 8100; 
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader (
                        new InputStreamReader(socket.getInputStream())) ) {
            while(true){
                System.out.println("Enter request: ");
                String request = scanner.nextLine();
                if(request.equals("exit"))
                {
                    out.println(request);
                    break;
                }
                out.println(request);
                String response = in.readLine();
                out.println(response);
                if(response.equals("Server stopped"))
                {
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
    }
}