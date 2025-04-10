package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;

public class ClientRobi extends JFrame {
    private JTextArea scriptArea;
    private JButton sendButton;
    private JTextArea responseArea;

    public ClientRobi() {
        setTitle("Robi Client");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setupUI();
    }

    private void setupUI() {
        scriptArea = new JTextArea("(space setColor black)\n(robi setColor yellow)");
        responseArea = new JTextArea();
        responseArea.setEditable(false);
        sendButton = new JButton("Envoyer");

        sendButton.addActionListener((ActionEvent e) -> sendScript());

        setLayout(new BorderLayout());
        add(new JScrollPane(scriptArea), BorderLayout.CENTER);
        add(sendButton, BorderLayout.SOUTH);
        add(new JScrollPane(responseArea), BorderLayout.EAST);
    }

    private void sendScript() {
        try (Socket socket = new Socket("localhost", 12345);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String script = scriptArea.getText().replace("\n", " ");
            String json = "{ \"txt\" : \"" + script + "\" }";

            out.write(script + "\n");
            out.flush();

            String response = in.readLine();
            responseArea.setText("Réponse serveur :\n" + response);

        } catch (IOException ex) {
            responseArea.setText("Erreur de connexion : " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	ClientRobi client = new ClientRobi();
            client.setVisible(true);
        });
    }
}
