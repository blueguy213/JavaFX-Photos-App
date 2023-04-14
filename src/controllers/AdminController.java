package controllers;

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
/**
 * The controller for the Admin view.
 * @author  Sree Kommalapati and Shreeti Patel
 */
public class AdminController implements Initializable {
    /**
     * The text field for entering a username for a new user.
     */
    @FXML
    private TextField adminUserField;
     
    /**
     * The list view for seeing all non-admin users.
     */
    @FXML
    private ListView<String> adminUserList;

     /**
     * The button for creating a new user.
     */
    @FXML
    private Button createUserButton;

     /**
     * The button for deleting the selected user.
     */
    @FXML
    private Button deleteUserButton;

     /**
     * The button to log out.
     */
    @FXML
    private Button logOutButton;

     /**
     * Private DataManger instance for AdminContoller.
     */
    private DataManager dataManager;

     /**
     * Creates a new user when create user button is clicked
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void onCreateUser(ActionEvent event) {

        String newUsername = adminUserField.getText().trim();
        if (newUsername.isEmpty() || newUsername.equals("admin")) {
            // Show an error dialog or message
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid Username", "The username you entered is invalid.");
            return;
        }

        if (dataManager.isUsernameTaken(newUsername)) {
            // Show an error dialog or message indicating the user already exists
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid Username", "The username you entered is already registered.");
            return;
        }

        // Add the new user to the list of users and update the list view
        dataManager.addUser(newUsername);
        dataManager.updateUserListView(adminUserList);

        // Clear the text field
        adminUserField.clear();
    }
     /**
     * Deletes a user when delete user button is clicked
     * @param event The ActionEvent that triggered this method.
     */
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

        // Remove the user from the list of users if it exists and update the list view
        dataManager.removeUser(selectedUser);
        dataManager.updateUserListView(adminUserList);
    }

    /**
     * Logs out when logout button is clicked
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void onLogOut(ActionEvent event) {
        JavaFXUtils.switchView(event, "/views/Login.fxml");
    }

   /**
    * Initializes the controller class for the corresponding FXML file.
    * It sets the data manager to be the singleton instance of the DataManager class and updates the admin user list view using the DataManager instance.
    * @param location The URL location of the FXML file.
    * @param resources The ResourceBundle used by the FXMLLoader.
    */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        dataManager = DataManager.getInstance();
        dataManager.updateUserListView(adminUserList);
    }   
}
