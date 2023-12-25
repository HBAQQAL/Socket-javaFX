package com.example.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {
    private static Socket socket = null;
    private static DataInputStream input = null;
    private static DataOutputStream out = null;
    private static String port = null;
    private static String host = null;
    private static String identity = null;
    private chatController controller;

    public Client(String host, String port, String identity, chatController controller) {
        if (socket != null) {
            return;
        }
        this.host = host;
        this.port = port;
        this.identity = identity;
        this.controller = controller;
        System.out.println("controller " + controller);
        this.start();
    }

    public void run() {
        try {
            socket = new Socket(host, Integer.parseInt(port));
            input = new DataInputStream(System.in);
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Connected");
            DataInputStream serverInput = new DataInputStream(socket.getInputStream());
            System.out.println(serverInput.readUTF());
            out.writeUTF(identity);
            System.out.println("Send identity");

            while (true) {
                System.out.println("Waiting for server response");
                String response = serverInput.readUTF();
                handleResponse(response);
            }
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    public static void sendMessage(String message) {
        try {
            System.out.println("Sending message from client");
            out.writeUTF(identity + " " + message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void handleResponse(String response) {
        System.out.println("Handling response");
        String responseArray[] = response.split(" ");
        String sender = responseArray[0];
        String message = responseArray[1];
        String receiver = responseArray[2];
        controller.addMessage(sender + ": " + message);

    }

}
