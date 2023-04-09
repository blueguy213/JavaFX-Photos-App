// Shreeti Patel and Sree Kommalapati
package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PhotoLibrary extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {			
		// set up FXML load
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/PhotoLib.fxml"));
		
		// load the fxml
		Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));

		Scene scene = new Scene(root, 673, 510);
		primaryStage.setScene(scene);
		primaryStage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}
}
