package controllers;

import java.net.URL;

import java.util.ResourceBundle;

import java.time.LocalDate;

import javafx.event.ActionEvent;

import javafx.scene.layout.TilePane;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import javafx.scene.image.ImageView;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import model.DataManager;

import utils.JavaFXUtils;


public class SearchController implements Initializable {

    @FXML
    private Label albumNameLable;

    @FXML
    private ChoiceBox<String> andOrChoiceBox;

    @FXML
    private Button dateSearchButton;

    @FXML
    private Button exportNewAlbumButton;

    @FXML
    private ChoiceBox<String> firstTagChoiceBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Button logOutButton;

    @FXML
    private Button nextPhotoButton;

    @FXML
    private TextArea photoDescriptionTextArea;

    @FXML
    private ImageView photoDisplayImageView;

    @FXML
    private Button prevPhotoButton;

    @FXML
    private Button searchPhotoButton;

    @FXML
    private ChoiceBox<String> secondTagChoiceBox;

    @FXML
    private TextField firstTagField;

    @FXML
    private TextField secondTagField;

    @FXML
    private Button tagSearchButton;

    @FXML
    private Button viewAlbumButton;

    @FXML
    private TilePane thumbnailTilePane;

    private DataManager dataManager;

    /**
     * Handles the serch by date button click event. 
     */
    @FXML
    void handleDateSearchButtonClick(ActionEvent event) {
        
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        dataManager.filterSearchResultsByDateRange(startDate, endDate);

        dataManager.displayThumbnailsOn(thumbnailTilePane);
    }

    @FXML
    void handleExportNewAlbumButtonClick(ActionEvent event) {

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
        dataManager.displaySelectedPhotoOn(photoDisplayImageView, photoDescriptionTextArea);
    }

    @FXML
    void handlePrevPhotoButtonClick(ActionEvent event) {
        dataManager.previousPhoto();
        dataManager.displaySelectedPhotoOn(photoDisplayImageView, photoDescriptionTextArea);
    }

    @FXML
    void handleSearchPhotosButtonClick(ActionEvent event) {

    }

    @FXML
    void handleTagSearchButtonClick(ActionEvent event) {


    }

    @FXML
    void handleViewAlbumButtonClick(ActionEvent event) {
        JavaFXUtils.switchView(event, "/views/User.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataManager = DataManager.getInstance();
        dataManager.prepareSearchResults();
        dataManager.displaySelectedPhotoOn(photoDisplayImageView, photoDescriptionTextArea);
        dataManager.displayThumbnailsOn(thumbnailTilePane);
    }
}
