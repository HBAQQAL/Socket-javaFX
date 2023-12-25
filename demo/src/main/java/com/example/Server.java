package com.example;

import java.net.ServerSocket;
import java.util.ArrayList;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private int port;
    static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    private String id;

    public Server(int port) {
        this.port = port;
        this.start();
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            while (true) {
                System.out.println("Waiting for a client ...");
                ClientHandler client = new ClientHandler(serverSocket.accept(), id);
                clients.add(client);
                System.out.println("Client accepted");
                for (ClientHandler c : clients) {
                    System.out.println("new client " + c.id);
                }

            }
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static ClientHandler getClient(String id) {
        for (ClientHandler c : clients) {
            if (c.id.equals(id)) {
                return c;
            }
        }
        return null;
    }

    public static void SendListOfClients() {
        String list = "";
        for (ClientHandler c : clients) {
            list += c.id + ",";
        }
        for (ClientHandler c : clients) {
            System.out.println("sending list to " + c.id);
            c.sendMessage(new Message(list, "server", c.id, "contacts").toString());
        }
    }
}
