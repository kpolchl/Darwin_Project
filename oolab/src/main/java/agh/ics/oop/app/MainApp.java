package agh.ics.oop.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //load first window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulationParameters.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Map Parameters");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
