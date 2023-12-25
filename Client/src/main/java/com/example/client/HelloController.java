package com.example.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField nameField;

    @FXML
    protected void onHelloButtonClick(ActionEvent event) throws Exception {
        // get the root from the event
        Node node = (Node) event.getSource();
        // get the stage from the root
        Stage stage = (Stage) node.getScene().getWindow();
        // create a new FXMLLoader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        // load the FXML file
        Parent root = loader.load();
        // get the controller
        chatController controller = loader.getController();
        // create an instance of the client
        Client client = new Client("localhost", "5000", nameField.getText(), controller);
        // set the scene to the stage
        stage.setScene(new Scene(root));
        // show the stage
        stage.show();

    }
}