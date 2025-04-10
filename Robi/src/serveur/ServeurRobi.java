package serveur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurRobi {
    public static void main(String[] args) {
        int port = 12345;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("RobiServer démarré sur le port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté : " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Script reçu : " + line);

                    String response = ScriptExecutor.executeScript(line);
                    out.write(response + "\n");
                    out.flush();
                }

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
