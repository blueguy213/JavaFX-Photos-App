// Shreeti Patel and Sree Kommalapati
package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PhotoLibrary extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {			
		// set up FXML loader
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/PhotoLib.fxml"));
		
		// load the fxml
		AnchorPane root = (AnchorPane)loader.load();

		Scene scene = new Scene(root, 673, 510);
		primaryStage.setScene(scene);
		primaryStage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}
}
