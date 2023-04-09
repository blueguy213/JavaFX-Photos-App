package controllers;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.User;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;

    private ArrayList<User> users;

    public LoginController() {
        try {
            File usersFile = new File("db/users");
            if (usersFile.exists()) {
                FileInputStream fis = new FileInputStream(usersFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                users = readArrayListOfUsers(ois);
                ois.close();
            } else {
                users = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<User> readArrayListOfUsers(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        Object deserializedObject = ois.readObject();
    
        if (!(deserializedObject instanceof ArrayList)) {
            throw new ClassCastException("Deserialized object is not an ArrayList.");
        }
    
        ArrayList<?> deserializedList = (ArrayList<?>) deserializedObject;
        ArrayList<User> users = new ArrayList<>();
    
        for (Object element : deserializedList) {
            if (element instanceof User) {
                users.add((User) element);
            } else {
                throw new ClassCastException("Deserialized object is not an instance of User.");
            }
        }
    
        return users;
    }

    @FXML
    public void handleLoginButtonClick(ActionEvent event) {
        // Get the input username
        String username = usernameField.getText();

        // Check if the entered username is "admin" or another registered user
        boolean userFound = false;
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                userFound = true;
                // Load the appropriate view and transition to it
                try {
                    Parent viewRoot;
                    if ("admin".equalsIgnoreCase(username)) {
                        viewRoot = FXMLLoader.load(getClass().getResource("/views/Admin.fxml"));
                    } else {
                        viewRoot = FXMLLoader.load(getClass().getResource("/views/User.fxml"));
                    }
                    Scene viewScene = new Scene(viewRoot);
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.setScene(viewScene);
                    currentStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        if (!userFound) {
            // Display an error message when the username is not registered
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Username");
            alert.setContentText("The username you entered is not registered.");
            alert.showAndWait();
        }
    }
}