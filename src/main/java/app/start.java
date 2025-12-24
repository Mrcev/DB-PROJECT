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
            // Note: Ensure mainPage.fxml is in the same directory as this class
            // or update the path to "/app/mainPage.fxml" if in a resource folder.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
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
