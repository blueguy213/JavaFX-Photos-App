package utils;

import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

/**
 * A utility class for JavaFX related tasks such as showing alerts and switching views.
 * @author Shreeti Patel and Sree Kommalapati
 */
public class JavaFXUtils {

    /**
     * Shows an alert with the given parameters.
     * @param alertType The type of alert to show.
     * @param title The title of the alert.
     * @param header The header of the alert.
     * @param content The content of the alert.
     */
    public static void showAlert(AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Switches the view to the view at the given path.
     * @param event The event that triggered the view switch.
     * @param viewPath The path to the view to switch to.
     * 
     * @see javafx.event.ActionEvent
     * @see javafx.fxml.FXMLLoader
     */
    public static void switchView(ActionEvent event, String viewPath) {
        try {
            Parent targetViewRoot = FXMLLoader.load(JavaFXUtils.class.getResource(viewPath));
            Scene targetViewScene = new Scene(targetViewRoot);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(targetViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}