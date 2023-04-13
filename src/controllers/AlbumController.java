package controllers;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.DataManager;
import model.Photo;
import utils.JavaFXUtils;

public class AlbumController implements Initializable{

    /**
     * The button for adding a photo to the album.
     */
    @FXML
    private Button addPhotoButton;
    
    /**
     * The text field for the path for a photo to add to the album.
     */
    @FXML
    private TextField addPhotoPathField;

    /**
     * The button for adding a tag to the photo.
     */
    @FXML
    private Button addTagButton;

    /**
     * The text field for adding a tag key.
     */
    @FXML
    private TextField addTagKeyField;

    /**
     * The text field for adding a tag value.
     */
    @FXML
    private TextField addTagValueField;

    /**
     * The button for copying a photo to another album.
     */
    @FXML
    private Label albumNameLable;

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
    private ChoiceBox<?> destinationAlbumChoiceBox;

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
     * The choice box for selecting a tag key to delete.
     */
    @FXML
    private ListView<?> photoCaptionListView;

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
    private ChoiceBox<?> selectTagToDeleteChoiceBox;

    /**
     * The choice box for selecting a tag key to delete.
     */
    @FXML
    private Button updateCaptionButton;

    /**
     * The button for updating the caption of the photo.
     */
    @FXML
    private Button viewAlbumButton;

    private DataManager dataManager;
    @FXML
    void handleAddPhotoButtonClick(ActionEvent event) {
        String inputPath = addPhotoPathField.getText();
        Photo newPhoto = new Photo(inputPath,"");        
        
    }

    @FXML
    void handleAddTagButtonClick(ActionEvent event) {

    }

    @FXML
    void handleCopyPhotoButtonClick(ActionEvent event) {

    }

    @FXML
    void handleDeleteTagButtonClick(ActionEvent event) {

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
    void handleMovePhotoButtonClick(ActionEvent event) {

    }

    @FXML
    void handleNextPhotoButtonClick(ActionEvent event) {
        // DataManager dmInstance = DataManager.getInstance();
        // dmInstance.selectedAlbum
        
    }

    @FXML
    void handlePrevPhotoButtonClick(ActionEvent event) {

    }

    @FXML
    void handleRemovePhotoButtonClick(ActionEvent event) {

    }

    @FXML
    void handleSearchPhotosButtonClick(ActionEvent event) {
        JavaFXUtils.switchView(event, "/views/Search.fxml");
    }

    @FXML
    void handleUpdateCaptionButtonClick(ActionEvent event) {

    }

    @FXML
    void handleViewAlbumButtonClick(ActionEvent event) {
        JavaFXUtils.switchView(event, "/views/User.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataManager = DataManager.getInstance();
        albumNameLable.setText(dataManager.getOpenedAlbumName());
        
    }

}
