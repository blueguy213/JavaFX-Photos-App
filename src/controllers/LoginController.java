package controllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

import model.User;
import model.Album;
import model.Photo;
import model.DataManager;

import utils.JavaFXUtils;
import utils.ImageUtils;

public class LoginController {
    
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;

    private ArrayList<User> users;

    public LoginController() {
        users = new ArrayList<User>();
        DataManager.readUsers(users);
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

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                viewPath = "/views/User.fxml";
                UserController.setLoggedInUser(user);
                JavaFXUtils.switchView(event, viewPath);
                return;
            }
        }
        JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid Username", "The username you entered is not registered.");
    }
}