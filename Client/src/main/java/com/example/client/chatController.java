package com.example.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class chatController {

    @FXML
    private VBox chatBox;
    @FXML
    private TextField messageBox;
    @FXML
    private TextField destinationBox;

    public void SendMessage() {
        String message = messageBox.getText() + " " + destinationBox.getText();
        Client.sendMessage(message);
        messageBox.clear();
        destinationBox.clear();
    }

    public void addMessage(String message) {
        Platform.runLater(() -> {
            chatBox.getChildren().add(new Label(message));
        });
    }

}
