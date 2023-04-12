package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class SearchController {

    @FXML
    private Label albumNameLable;

    @FXML
    private AnchorPane albumPane;

    @FXML
    private ChoiceBox<?> andOrChoiceBox;

    @FXML
    private Button dateSearchButton;

    @FXML
    private Button exportNewAlbumButton;

    @FXML
    private ChoiceBox<?> firstTagChoiceBox;

    @FXML
    private TextField firstTagField;

    @FXML
    private TextField endDateField;

    @FXML
    private Button logOutButton;

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
    private Button searchPhotoButton;

    @FXML
    private ChoiceBox<?> secondTagChoiceBox;

    @FXML
    private TextField secondTagField;

    @FXML
    private TextField startDateField;

    @FXML
    private Button tagSearchButton;

    @FXML
    private Button viewAlbumButton;

    private String endDateInput;
    private String startDateInput;
    private String firstTagInput;
    private String secondTagInput;

  
    /**
     * Handles the serch by date button click event. 
     */
    @FXML
    void handleDateSearchButtonClick(ActionEvent event) {
        // if both start and end are given, get within range
        if ((!startDateField.getText().isEmpty())&&(!endDateField.getText().isEmpty())){
            // search for photos in range
            
        }
        if ((!startDateField.getText().isEmpty())&&(endDateField.getText().isEmpty())){ //if only start is given get all photos after start date
            // search for photos in range
        }
        if ((startDateField.getText().isEmpty())&&(!endDateField.getText().isEmpty())){ // if only end is given, get all photos before end date
            // search for photos in range
        }
       

    }

    @FXML
    void handleExportNewAlbumButtonClick(ActionEvent event) {

    }

    @FXML
    void handleLogOutButtonClick(ActionEvent event) {

    }

    @FXML
    void handleNextPhotoButtonClick(ActionEvent event) {

    }

    @FXML
    void handlePrevPhotoButtonClick(ActionEvent event) {

    }

    @FXML
    void handleSearchPhotosButtonClick(ActionEvent event) {

    }

    @FXML
    void handleTagSearchButtonClick(ActionEvent event) {


    }

    @FXML
    void handleViewAlbumButtonClick(ActionEvent event) {

    }

}
