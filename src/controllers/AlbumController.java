package controllers;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ResourceBundle;

import java.io.File;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.layout.TilePane;

import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import model.DataManager;

import utils.JavaFXUtils;
/**
 * The controller for the Album view.
 * @author  Sree Kommalapati and Shreeti Patel
 */
public class AlbumController implements Initializable {

    /**
     * The button for adding a photo to the album.
     */
    @FXML
    private Button addPhotoButton;
    
    /**
     * The text field for the path for a photo to add to the album.
     */
    @FXML
    private TextField addPhotoCaptionField;

    @FXML
    private CheckBox isTagUnique;

    /**
     * The button for adding a tag to the photo.
     */
    @FXML
    private Button addTagButton;

    /**
     * The text field for adding a tag key.
     */
    @FXML
    private ComboBox<String> addTagKeyBox;

    /**
     * The text field for adding a tag value.
     */
    @FXML
    private ComboBox<String> addTagValueBox;

    /**
     * The button for copying a photo to another album.
     */
    @FXML
    private Label albumNameLabel;

    /**
     * The pane for displaying the album.
     */
    @FXML
    private AnchorPane albumPane;

    /**
     * The button for deleting a tag from the photo.
     */
    @FXML
    private Button copyPhotoButton;

    /**
     * The button for deleting a tag from the photo.
     */
    @FXML
    private Button deleteTagButton;

    /**
     * The text field for entering a date to search for.
     */
    @FXML
    private ChoiceBox<String> destinationAlbumChoiceBox;

    /**
     * The button for searching for a date.
     */
    @FXML
    private Button logOutButton;

    /**
     * The button for moving a photo to another album.
     */
    @FXML
    private Button movePhotoButton;

    /**
     * The text field for entering a new caption.
     */
    @FXML
    private TextField newCaptionField;

    /**
     * The button for displaying the next photo.
     */
    @FXML
    private Button nextPhotoButton;

    /**
     * The text area for displaying the photo description (datetime, caption, tags).
     */
    @FXML
    private TextArea photoDescriptionTextArea;

    /**
     * The list view for displaying the photo captions.
     */
    @FXML
    private ImageView photoDisplayImageView;

    /**
     * The image view for displaying the photo.
     */
    @FXML
    private Pane photoDisplayPane;

    /**
     * The pane for displaying the photo.
     */
    @FXML
    private ScrollPane photoThumbScrollPane;

    /**
     * The scroll pane for displaying the photo thumbnails.
     */
    @FXML
    private Button prevPhotoButton;

    /**
     * The button for displaying the previous photo.
     */
    @FXML
    private Button removePhotoButton;

    /**
     * The button for removing a photo from the album.
     */
    @FXML
    private Button searchPhotoButton;

    /**
     * The button for searching for a photo.
     */
    @FXML
    private ChoiceBox<String> selectTagToDeleteChoiceBox;

    /**
     * The choice box for selecting a tag key to delete.
     */
    @FXML
    private Button updateCaptionButton;

    /**
     * The button for updating the caption of the photo.
     */
    @FXML
    private Button viewAlbumsButton;

    /**
     * The TilePane for displaying the photo thumbnails.
     */
    @FXML
    private TilePane thumbnailTilePane;

    /**
     * The data manager instance for the application.
     */
    private DataManager dataManager;

    /**
     * Updates display on Album view
     */
    private void updateDisplay() {
        dataManager.displaySelectedPhotoOn(photoDisplayImageView, photoDescriptionTextArea);
        dataManager.displayThumbnailsOn(thumbnailTilePane);
        dataManager.displayUnopenedAlbumsOn(destinationAlbumChoiceBox);
        dataManager.displayDeletableTagsOn(selectTagToDeleteChoiceBox);
        dataManager.displayCreatableTagsOn(addTagKeyBox, addTagValueBox);
    }

    /**
     * Handles the add photo button click event.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleAddPhotoButtonClick(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Photo");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Images", "*.bmp", "*.gif", "*.jpg", "*.jpeg", "*.png"),
            new FileChooser.ExtensionFilter("BMP", "*.bmp"),
            new FileChooser.ExtensionFilter("GIF", "*.gif"),
            new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg"),
            new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                String selectedFileUrl = selectedFile.toURI().toURL().toString();

                if (dataManager.hasPhoto(selectedFileUrl)) {
                    JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Photo already exists!", "The photo you selected already exists in the album.");
                    return;
                }

                String caption = addPhotoCaptionField.getText();
                dataManager.addPhotoToOpenedAlbum(selectedFileUrl, caption);
                updateDisplay();
            } catch (MalformedURLException e) {
                JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid File", e.getMessage());
            }
        } else {
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Enter a file!", "You did not select a file that exists.");
        }
    }

    /**
     * Handles the add tag button click event.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleAddTagButtonClick(ActionEvent event) {
        // Get the key and value from the text fields.
        String key = addTagKeyBox.getValue();
        String value = addTagValueBox.getValue();

        // Check if the key and value are valid.
        if (key.isEmpty() || value.isEmpty()) {
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid Tag", "You must enter a key and value for the tag.");
            return;
        }

        // Check if the tag should be unique
        if (isTagUnique.isSelected() && dataManager.hasTagKey(key)) {
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid Tag", "That tag is unique and already exists.");
            return;
        }

        // Add the tag to the photo.
        dataManager.addTagToSelectedPhoto(key, value, isTagUnique.isSelected());

        updateDisplay();
    }

    /**
     * Handles the copy photo button click event.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleCopyPhotoButtonClick(ActionEvent event) {
        String destinationAlbumName = destinationAlbumChoiceBox.getValue();
        dataManager.copySelectedPhotoToAlbum(destinationAlbumName);
    }

    /**
     * Handles the delete tag button click event.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleDeleteTagButtonClick(ActionEvent event) {
        // Get the key and value from the choice boxes.
        String tag = selectTagToDeleteChoiceBox.getValue();
        //System.out.println("Tag: " + tag);

        // Delete the tag from the photo.
        dataManager.deleteTagFromSelectedPhoto(tag);


        updateDisplay();
    }

    /**
     * Handles the logout button click event.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleLogOutButtonClick(ActionEvent event) {
        dataManager.closeAlbum();
        dataManager.logOut();
        JavaFXUtils.switchView(event, "/views/Login.fxml");
    }

    /**
     * Handles the Move Photo button click event.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleMovePhotoButtonClick(ActionEvent event) {
        String destinationAlbumName = destinationAlbumChoiceBox.getValue();
        if (dataManager.copySelectedPhotoToAlbum(destinationAlbumName) > -1) {
            dataManager.removeSelectedPhoto();
        }
        updateDisplay();
    }

     /**
     * Handles the Next Photo button click event.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleNextPhotoButtonClick(ActionEvent event) {
        dataManager.nextPhoto();
        updateDisplay();
    }

     /**
     * Handles the Prev Photo button click event.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handlePrevPhotoButtonClick(ActionEvent event) {
        dataManager.previousPhoto();
        updateDisplay();
    }

     /**
     * Handles the Remove Photo button click event.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleRemovePhotoButtonClick(ActionEvent event) {
        dataManager.removeSelectedPhoto();
        updateDisplay();
    }

     /**
     * Handles the Search Photos button click event.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleSearchPhotosButtonClick(ActionEvent event) {
        dataManager.closeAlbum();
        JavaFXUtils.switchView(event, "/views/Search.fxml");
    }

     /**
     * Handles the Update Caption button click event.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleUpdateCaptionButtonClick(ActionEvent event) {
        String newCaption = newCaptionField.getText();
        dataManager.setCaption(newCaption);
        updateDisplay();
    }
    
    /**
     * Handles the View Albums button click event.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void handleViewAlbumsButtonClick(ActionEvent event) {
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
        albumNameLabel.setText(dataManager.getOpenedAlbumName());
        updateDisplay();
    }

}