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
import utils.JavaFXUtils;

public class AlbumController implements Initializable{

    @FXML
    private Button addPhotoButton;

    @FXML
    private Button addTagButton;

    @FXML
    private TextField addTagKeyField;

    @FXML
    private TextField addTagValueField;

    @FXML
    private Label albumNameLable;

    @FXML
    private AnchorPane albumPane;

    @FXML
    private Button copyPhotoButton;

    @FXML
    private Button deleteTagButton;

    @FXML
    private ChoiceBox<?> destinationAlbumChoiceBox;

    @FXML
    private Button logOutButton;

    @FXML
    private Button movePhotoButton;

    @FXML
    private TextField newCaptionField;

    @FXML
    private Button nextPhotoButton;

    @FXML
    private ListView<?> photoCaptionListView;

    @FXML
    private ImageView photoDisplayImageView;

    @FXML
    private Pane photoDisplayPane;

    @FXML
    private ScrollPane photoThumbScrollPane;

    @FXML
    private Button prevPhotoButton;

    @FXML
    private Button removePhotoButton;

    @FXML
    private Button searchPhotoButton;

    @FXML
    private ChoiceBox<?> selectTagToDeleteChoiceBox;

    @FXML
    private Button updateCaptionButton;

    @FXML
    private Button viewAlbumButton;

    private DataManager dataManager;
    @FXML
    void handleAddPhotoButtonClick(ActionEvent event) {
        
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
    }

}
