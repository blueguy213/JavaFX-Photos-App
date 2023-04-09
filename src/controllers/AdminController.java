package controllers;

import model.User;

import java.io.*;

import java.util.ArrayList;
import java.util.ResourceBundle;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



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

    private static final String USERS_FILE = "db/users";
    ArrayList<User> users = new ArrayList<>();

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
    public void onCreateUser(ActionEvent event) {
        String newUser = adminUserField.getText().trim();
        if (newUser.isEmpty()) {
            // Show an error dialog or message
            return;
        }
    
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            ArrayList<User> users = readArrayListOfUsers(ois);
    
            if (users.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(newUser))) {
                // Show an error dialog or message indicating the user already exists
                return;
            }
    
            users.add(new User(newUser));
            adminUserList.getItems().add(newUser);
    
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
                oos.writeObject(users);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        adminUserField.clear();
    }

    @FXML
    public void onDeleteUser(ActionEvent event) {
        String selectedUser = adminUserList.getSelectionModel().getSelectedItem();
        if (selectedUser == null || selectedUser.equalsIgnoreCase("admin")) {
            // Show an error dialog or message indicating that the admin user cannot be deleted
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            ArrayList<User> users = readArrayListOfUsers(ois);

            users.removeIf(user -> user.getUsername().equalsIgnoreCase(selectedUser));

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
                oos.writeObject(users);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        adminUserList.getItems().remove(selectedUser);
    }

    @FXML
    public void onLogOut(ActionEvent event) {
        // Close the current stage and open the Login stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            loginStage.setTitle("Photo Library");
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        File usersFile = new File("db/users");
        if (!usersFile.exists()) {
            try {
                usersFile.getParentFile().mkdirs();
                usersFile.createNewFile();
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
                    ArrayList<User> users = new ArrayList<>();
                    users.add(new User("admin"));
                    oos.writeObject(users);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile))) {
            ArrayList<User> users = readArrayListOfUsers(ois);
            for (User user : users) {
                adminUserList.getItems().add(user.getUsername());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    

    
}
