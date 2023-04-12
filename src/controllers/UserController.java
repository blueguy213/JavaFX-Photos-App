package controllers;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import javafx.event.ActionEvent;

import java.util.ResourceBundle;

import model.Album;
import model.DataManager;

import utils.JavaFXUtils;

/**
 * The controller for the User view.
 * @author  Sree Kommalapati and Shreeti Patel
 */
public class UserController implements Initializable {

    /**
     * The text field for renaming an album.
     */
    @FXML
    private TextField userRenameAlbumField;
    
    /**
     * The text field for creating a new album.
     */
    @FXML
    private TextField userCreateAlbumField;
    
    /**
     * The ListView for displaying the user's albums.
     */
    @FXML
    private ListView<Album> albumsListView;
    
    /**
     * The label for displaying the selected album's name.
     */
    @FXML
    private Label selectedAlbumName;
    
    /**
     * The label for displaying the selected album's photo count.
     */
    @FXML
    private Label selectedAlbumPhotoCount;
    
    /**
     * The label for displaying the selected album's date range.
     */
    @FXML
    private Label selectedAlbumDateRange;

    /**
     * The DataManager instance for the application.
     */
    private DataManager dataManager;


    /**
     * Initializes the controller class and populates the albumsListView with the user's albums.
     * 
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Get the logged in user
        dataManager = DataManager.getInstance();

        // Add the album names to the ListView
        dataManager.updateAlbumListView(albumsListView);

        // Set the listener for the albumsListView
        albumsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateAlbumInfo(newSelection);
            }
        });

        // Select the first album in the list
        albumsListView.getSelectionModel().selectFirst();
    }

    /**
     * Handles the Update Album button click event
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void handleUpdateSelectedAlbumButtonClick(ActionEvent event) {
        // Implement the logic for updating the album name
        String newName = userRenameAlbumField.getText().trim();
        // Check if the new name is empty
        if (newName.isEmpty()) {
            // Show an error dialog or message
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "No Name Entered", "You must enter a name.");
            return;
        }
        // Check if the new name is already in use
        if (dataManager.isAlbumNameTaken(newName)) {
            // Show an error dialog or message indicating the album name is already in use
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid Album Name", "The album name you entered is already in use.");
            return;
        }
        Album selectedAlbum = albumsListView.getSelectionModel().getSelectedItem();
        if (selectedAlbum == null) {
            // Show an error dialog or message indicating the user must select an album
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "No Album Selected", "You must select an album to update.");
            return;
        }
        selectedAlbum.setName(newName);
        albumsListView.refresh();
    }

    /**
     * Handles the Delete Album button click event
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void handleDeleteSelectedAlbumButtonClick(ActionEvent event) {
        // Delete the selected album from the user's albums list and the albumsListView
        Album selectedAlbum = albumsListView.getSelectionModel().getSelectedItem();
        if (selectedAlbum == null) {
            // Show an error dialog or message indicating the user must select an album
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "No Album Selected", "You must select an album to delete.");
            return;
        }
        dataManager.removeAlbum(selectedAlbum);
        albumsListView.getItems().remove(selectedAlbum);
        albumsListView.getSelectionModel().selectFirst();
    }

    /**
     * Handles the Create Album button click event
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void handleCreateAlbumButtonClick(ActionEvent event) {
        // Create a new album with the name entered in the userCreateAlbumField
        String name = userCreateAlbumField.getText().trim();
        // Check if the name is empty
        if (name.isEmpty()) {
            // Show an error dialog or message
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "No Name Entered", "You must enter a name.");
            return;
        }
        // Check if the name is already in use
        if (dataManager.isAlbumNameTaken(name)) {
            // Show an error dialog or message indicating the album name is already in use
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid Album Name", "The album name you entered is already in use.");
            return;
        }
        Album album = new Album(name);
        dataManager.addAlbum(album);
        dataManager.updateAlbumListView(albumsListView);
        albumsListView.getSelectionModel().select(album);
    }

    /**
     * Handles the Open Album button click event
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void handleOpenSelectedAlbumButtonClick(ActionEvent event) {
        System.out.println("Open Album button clicked");
        // Implement the logic for opening the selected album
        Album selectedAlbum = albumsListView.getSelectionModel().getSelectedItem();
        if (selectedAlbum == null) {
            // Show an error dialog or message indicating the user must select an album
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "No Album Selected", "You must select an album to open.");
            return;
        }
        dataManager.openAlbum(selectedAlbum);
        JavaFXUtils.switchView(event, "/views/Album.fxml");
    }

    /**
     * Handles the Search Photos button click event
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void handleSearchPhotosButtonClick(ActionEvent event) {
        System.out.println("Search Photos button clicked");
        // Switch to the Search view
        JavaFXUtils.switchView(event, "/views/Search.fxml");
    }

    /**
     * Handle the Log Out button click event
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void handleLogOutButtonClick(ActionEvent event) {
        // Save the user data
        dataManager.writeUsers();
        // Set the loggedInUser to null and switch to the Login view
        dataManager.logOut();
        JavaFXUtils.switchView(event, "/views/Login.fxml");
    }

    /**
     * Updates the album information (name, photo count, and date range) in the UI.
     * @param album The album to update the information for.
     */
    private void updateAlbumInfo(Album album) {

        // Implement the logic for updating the album information (name, photo count, and date range)
        selectedAlbumName.setText(album.getName());
        if (album.getPhotos().size() == 1) {
            selectedAlbumPhotoCount.setText(album.getPhotos().size() + " photo");
        } else {
            selectedAlbumPhotoCount.setText(album.getPhotos().size() + " photos");
        }
        selectedAlbumDateRange.setText(album.getDateRange());
    }
}