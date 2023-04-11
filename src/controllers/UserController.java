package controllers;

import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import javafx.event.ActionEvent;

import java.util.ResourceBundle;

import model.User;
import model.Album;
import model.DataManager;

import utils.JavaFXUtils;

/**
 * The controller for the User view.
 * @author  Sree Kommalapati and Shreeti Patel
 */
public class UserController implements Initializable {
    
    @FXML
    private TextField userRenameAlbumField;
    
    @FXML
    private TextField userCreateAlbumField;
    
    @FXML
    private ListView<Album> albumsListView;
    
    @FXML
    private Label selectedAlbumName;
    
    @FXML
    private Label selectedAlbumPhotoCount;
    
    @FXML
    private Label selectedAlbumDateRange;


    private static User loggedInUser;

    /**
     * Initializes the controller class and populates the albumsListView with the user's albums.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Populate the albumsListView with the user's albums using ObservableList
        ObservableList<Album> albums = FXCollections.observableArrayList();

        // Add the album names to the ObservableList
        albums.addAll(loggedInUser.getAlbums());
        albumsListView.setItems(albums);

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
        if (loggedInUser.getAlbums().stream().anyMatch(album -> album.getName().equals(newName))) {
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
        loggedInUser.removeAlbum(selectedAlbum);
        albumsListView.getItems().remove(selectedAlbum);
        albumsListView.getSelectionModel().selectFirst();
    }

    /**
     * Handles the Create Album button click event
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
        if (loggedInUser.getAlbums().stream().anyMatch(album -> album.getName().equals(name))) {
            // Show an error dialog or message indicating the album name is already in use
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid Album Name", "The album name you entered is already in use.");
            return;
        }
        Album album = new Album(name);
        loggedInUser.addAlbum(album);
        albumsListView.getItems().add(album);
        albumsListView.getSelectionModel().select(album);
    }

    /**
     * Handles the Open Album button click event
     */
    @FXML
    public void handleOpenSelectedAlbumButtonClick(ActionEvent event) {
        // Implement the logic for opening the selected album
    }

    /**
     * Handles the Search Photos button click event
     */
    @FXML
    public void handleSearchPhotosButtonClick(ActionEvent event) {
        // Switch to the Search view
    }

    /**
     * Handle the Log Out button click event
     */
    @FXML
    public void handleLogOutButtonClick(ActionEvent event) {
        // Set the loggedInUser to null and switch to the Login view
        loggedInUser = null;
        JavaFXUtils.switchView(event, "/views/Login.fxml");
    }


    /**
     * Handles the Log Out event
     */
    @FXML
    public void handleLogOut(ActionEvent event) {
        // Set the loggedInUser to null and switch to the Login view
        loggedInUser = null;
        JavaFXUtils.switchView(event, "/views/Login.fxml");
    }

    /**
     * Updates the album information (name, photo count, and date range) in the UI.
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

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }
}