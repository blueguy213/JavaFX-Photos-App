package controllers;

import java.net.URL;

import java.util.ResourceBundle;

import java.time.LocalDate;

import javafx.event.ActionEvent;

import javafx.scene.layout.TilePane;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.image.ImageView;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import model.DataManager;

import utils.JavaFXUtils;


public class SearchController implements Initializable {

    /**
     * The DatePicker for selecting the start date to search by.
     */
    @FXML
    private DatePicker startDatePicker;

    /**
     * The DatePicker for selecting the end date to search by.
     */
    @FXML
    private DatePicker endDatePicker;

    /**
     * The Button for searching by date.
     */
    @FXML
    private Button dateSearchButton;

    /**
     * The Button for going to the previous photo.
     */
    @FXML
    private Button prevPhotoButton;

    /**
     * The Button for going to the next photo.
     */
    @FXML
    private Button nextPhotoButton;

    /**
     * The ChoiceBox for selecting the first tag to serach by.
     */
    @FXML
    private ChoiceBox<String> firstTagChoiceBox;
    
     /**
     * The ChoiceBox for selecting and/or to set a condition to search with a second tag. 
     */
    @FXML
    private ChoiceBox<String> andOrChoiceBox;

     /**
     * The ChoiceBox for selecting the second tag to serach by.
     */
    @FXML
    private ChoiceBox<String> secondTagChoiceBox;

     /**
     * The button to search by tags.
     */
    @FXML
    private Button tagSearchButton;

     /**
     * The TilePane to view thumbnails of photos.
     */
    @FXML
    private TilePane thumbnailTilePane;

    /**
     * The ImageView to view the display of the selected photo.
     */
    @FXML
    private ImageView photoDisplayImageView;

    /**
     * The TextArea for the photo description.
     */
    @FXML
    private TextArea photoDescriptionTextArea;

    /**
     * The TextField for the export destination.
     */
    @FXML
    private TextField exportDestinationTextField;

    /**
     * The Button to export as a new album.
     */
    @FXML
    private Button exportNewAlbumButton;

    /**
     * The Button for View Album.
     */
    @FXML
    private Button viewAlbumButton;

    /**
     * The Button for Search Photo.
     */
    @FXML
    private Button searchPhotoButton;

    /**
     * The Button for LogOut.
     */
    @FXML
    private Button logOutButton;

    /**
     * The DataManager instance for the Search Controller.
     */
    private DataManager dataManager;

    /**
     * Updates display on Search view
     */
    private void updateDisplay() {
        dataManager.displaySelectedPhotoOn(photoDisplayImageView, photoDescriptionTextArea);
        dataManager.displayThumbnailsOn(thumbnailTilePane);
        dataManager.displayAllTagTypesOn(firstTagChoiceBox);
        dataManager.displayAllTagTypesOn(secondTagChoiceBox);
    }

    /**
     * Handles the serch by date button click event. 
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleDateSearchButtonClick(ActionEvent event) {
        
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        dataManager.filterSearchResultsByDateRange(startDate, endDate);

        updateDisplay();
    }
    /**
     * Handles the Export New Album button click event
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleExportNewAlbumButtonClick(ActionEvent event) {
        String exportAlbumName = exportDestinationTextField.getText();
        if (exportAlbumName == null || exportAlbumName.isEmpty()) {
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "No album name entered", "Please enter a name for the new album.");
            return;
        } else if (dataManager.hasAlbum(exportAlbumName)) {
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Album already exists", "An album with that name already exists. Please enter a different name.");
            return;
        }
        dataManager.exportSearchResultsToAlbum(exportAlbumName);
    }

     /**
     * Handles the Log Out button click event
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleLogOutButtonClick(ActionEvent event) {
        // Save the user data
        dataManager.writeUsers();
        // Set the loggedInUser to null and switch to the Login view
        dataManager.logOut();
        JavaFXUtils.switchView(event, "/views/Login.fxml");
    }

     /**
     * Handles the Next Photo button click event
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleNextPhotoButtonClick(ActionEvent event) {
        dataManager.nextPhoto();
        updateDisplay();
    }

    /**
     * Handles the Prev Photo button click event
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handlePrevPhotoButtonClick(ActionEvent event) {
        dataManager.previousPhoto();
        updateDisplay();
    }

    /**
     * Handles the Search Photos button click event
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleSearchPhotosButtonClick(ActionEvent event) {
        dataManager.prepareSearchResults();
        updateDisplay();
    }

    /**
     * Handles the Tag Search Photo button click event
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleTagSearchButtonClick(ActionEvent event) {
        
        String firstTag = firstTagChoiceBox.getValue();
        String secondTag = secondTagChoiceBox.getValue();

        String firstTagKey = null;
        String firstTagValue = null;
        String secondTagKey = null;
        String secondTagValue = null;

        if (firstTag != null) {
            firstTagKey = firstTag.split(": ")[0];
            firstTagValue = firstTag.split(": ")[1];
        }

        if (secondTag != null) {
            secondTagKey = secondTag.split(": ")[0];
            secondTagValue = secondTag.split(": ")[1];
        }

        String andOr = andOrChoiceBox.getValue();

        if (firstTagKey == null && secondTagKey == null) {
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "No tags selected", "Please select at least one tag to search by.");
            return;
        } else if (firstTagKey == null) {
            dataManager.filterSearchResultsByOneTag(secondTagKey, secondTagValue);
        } else if (secondTagKey == null) {
            dataManager.filterSearchResultsByOneTag(firstTagKey, firstTagValue);
        } else if (andOr == null) {
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "No AND/OR selected", "Please select AND or OR to search by.");
            return;
        } else if (andOr.equals("AND")) {
            dataManager.filterSearchResultsByTwoTagsAnd(firstTagKey, firstTagValue, secondTagKey, secondTagValue);
        } else if (andOr.equals("OR")) {
            dataManager.filterSearchResultsByTwoTagsOr(firstTagKey, firstTagValue, secondTagKey, secondTagValue);
        } else {
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid AND/OR selection", "Fix needed :/");
            return;
        }

        updateDisplay();

    }
    /**
     * Handles the View Album button click event
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleViewAlbumButtonClick(ActionEvent event) {
        JavaFXUtils.switchView(event, "/views/User.fxml");
    }

    /**
     * Initializes the controller class for the corresponding FXML file.
     * It sets the data manager to be the singleton instance of the DataManager class.
     * @param location The URL location of the FXML file.
     * @param resources The ResourceBundle used by the FXMLLoader.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataManager = DataManager.getInstance();
        andOrChoiceBox.getItems().addAll("AND", "OR");
        dataManager.prepareSearchResults();
        updateDisplay();
    }
}
