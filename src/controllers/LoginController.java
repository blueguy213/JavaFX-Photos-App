package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

import model.DataManager;

import utils.JavaFXUtils;

public class LoginController {
    
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;

    private DataManager dataManager;

    public LoginController() {
        dataManager = DataManager.getInstance();
        dataManager.readUsers();
    }

    @FXML
    public void handleLoginButtonClick(ActionEvent event) {
        // Get the input username
        String username = usernameField.getText();
        // Check if the entered username is "admin" or another registered user
        String viewPath;

        if ("admin".equals(username)) {
            viewPath = "/views/Admin.fxml";
            JavaFXUtils.switchView(event, viewPath);
            return;
        }


        if (dataManager.isUsernameTaken(username)) {
            viewPath = "/views/User.fxml";
            dataManager.logIn(username);
            JavaFXUtils.switchView(event, viewPath);
            return;
        } else {
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid Username", "The username you entered is not registered.");
        }
    }
}