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

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Button dateSearchButton;

    @FXML
    private Button prevPhotoButton;

    @FXML
    private Button nextPhotoButton;

    @FXML
    private ChoiceBox<String> firstTagChoiceBox;
    
    @FXML
    private ChoiceBox<String> andOrChoiceBox;

    @FXML
    private ChoiceBox<String> secondTagChoiceBox;

    @FXML
    private Button tagSearchButton;

    @FXML
    private TilePane thumbnailTilePane;

    @FXML
    private ImageView photoDisplayImageView;

    @FXML
    private TextArea photoDescriptionTextArea;

    @FXML
    private TextField exportDestinationTextField;

    @FXML
    private Button exportNewAlbumButton;

    @FXML
    private Button viewAlbumButton;

    @FXML
    private Button searchPhotoButton;

    @FXML
    private Button logOutButton;

    private DataManager dataManager;

    private void updateDisplay() {
        dataManager.displaySelectedPhotoOn(photoDisplayImageView, photoDescriptionTextArea);
        dataManager.displayThumbnailsOn(thumbnailTilePane);
        dataManager.displayAllTagTypesOn(firstTagChoiceBox);
        dataManager.displayAllTagTypesOn(secondTagChoiceBox);
    }

    /**
     * Handles the serch by date button click event. 
     */
    @FXML
    void handleDateSearchButtonClick(ActionEvent event) {
        
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        dataManager.filterSearchResultsByDateRange(startDate, endDate);

        updateDisplay();
    }

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

    @FXML
    void handleLogOutButtonClick(ActionEvent event) {
        // Save the user data
        dataManager.writeUsers();
        // Set the loggedInUser to null and switch to the Login view
        dataManager.logOut();
        JavaFXUtils.switchView(event, "/views/Login.fxml");
    }

    @FXML
    void handleNextPhotoButtonClick(ActionEvent event) {
        dataManager.nextPhoto();
        updateDisplay();
    }

    @FXML
    void handlePrevPhotoButtonClick(ActionEvent event) {
        dataManager.previousPhoto();
        updateDisplay();
    }

    @FXML
    void handleSearchPhotosButtonClick(ActionEvent event) {
        dataManager.prepareSearchResults();
        updateDisplay();
    }

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

    @FXML
    void handleViewAlbumButtonClick(ActionEvent event) {
        JavaFXUtils.switchView(event, "/views/User.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataManager = DataManager.getInstance();
        andOrChoiceBox.getItems().addAll("AND", "OR");
        dataManager.prepareSearchResults();
        updateDisplay();
    }
}
