package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML from resources folder using absolute path
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/mainPage.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            primaryStage.setTitle("Database Frontend");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {e.printStackTrace();}}

    public static void main(String[] args) {
        launch(args);
    }
}
