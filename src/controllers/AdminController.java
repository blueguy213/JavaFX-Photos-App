package controllers;

import model.User;


import java.util.ArrayList;
import java.util.ResourceBundle;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import model.DataManager;

import utils.JavaFXUtils;

public class AdminController implements Initializable {

    @FXML
    private TextField adminUserField;

    @FXML
    private ListView<String> adminUserList;

    @FXML
    private Button createUserButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button logOutButton;


    @FXML
    public void onCreateUser(ActionEvent event) {
        String newUsername = adminUserField.getText().trim();
        if (newUsername.isEmpty()) {
            // Show an error dialog or message
            return;
        }
        DataManager.readUsers();
        if (DataManager.isUsernameTaken(newUsername)) {
            // Show an error dialog or message indicating the user already exists
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid Username", "The username you entered is already registered.");
            return;
        }
        // Add the new user to the list of users
        DataManager.addUser(newUsername);

        // Update the list view
        adminUserList.getItems().add(newUsername);

        // Write the updated list of users to the file
        DataManager.writeUsers();

        // Clear the text field
        adminUserField.clear();
    }

    @FXML
    public void onDeleteUser(ActionEvent event) {

        // Get the selected user from the list view
        String selectedUser = adminUserList.getSelectionModel().getSelectedItem();

        // Check if the selected user is the admin user or if no user is selected
        if (selectedUser == null) {
            // Show an error dialog or message indicating the selected user cannot be deleted
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid Username", "There is no user selected.");
            return;
        } else if (selectedUser.equals("admin")) {
            // Show an error dialog or message indicating the selected user cannot be deleted
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid Username", "The admin cannot be deleted.");
            return;
        }

        // Read the list of users from the file
        SerializationUtils.readUsers(users);

        // Remove the user from the list of users if it exists
        users.removeIf(user -> user.getUsername().equals(selectedUser));

        // Write the updated list of users to the file
        SerializationUtils.writeUsers(users);

        // Remove the user from the list view
        adminUserList.getItems().remove(selectedUser);
    }

    @FXML
    public void onLogOut(ActionEvent event) {
        JavaFXUtils.switchView(event, "/views/Login.fxml");
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        SerializationUtils.readUsers(users);
        for (User user : users) {
            adminUserList.getItems().add(user.getUsername());
        }
    }   
}