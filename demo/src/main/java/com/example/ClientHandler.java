package com.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    Socket serverSocket;
    private DataInputStream in = null;
    public String id;
    private static DataOutputStream out = null;

    public ClientHandler(Socket serverSocket, String id) {
        try {
            System.out.println("ClientHandler created");
            this.serverSocket = serverSocket;
            this.id = id;
            in = new DataInputStream(serverSocket.getInputStream());
            out = new DataOutputStream(serverSocket.getOutputStream());
            this.start();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public void run() {
        System.out.println("new ClientHandler started for client " + id + " ...");
        getUserId();
        String line = "";
        while (!line.equals("Over")) {
            try {
                line = in.readUTF();
                System.out.println(line);
                if (line != "exit") {
                    String c[] = line.split(" ");
                    String idReceiver = c[3];
                    System.out.println(" receiver id " + idReceiver);
                    Server.getClient(idReceiver).sendMessage(c[3]);
                } else {
                    Server.getClient(id).sendMessage("exit");
                    break;
                }
            } catch (IOException i) {
                System.out.println(i);
                break;
            }
        }
        System.out.println("Closing connection");
        CloseConnection();
    }

    public void getUserId() {
        try {
            out.writeUTF(" Server : Enter your id ");
            setId(in.readUTF().split(" ")[2]);
            System.out.println("ClientHandler id set to " + id);
            Server.SendListOfClients();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            System.out.println("Message sent: " + message);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public void CloseConnection() {
        try {
            serverSocket.close();
            in.close();
            out.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public void setId(String id) {
        this.id = id;
        System.out.println("ClientHandler id set to " + id);
    }

}
