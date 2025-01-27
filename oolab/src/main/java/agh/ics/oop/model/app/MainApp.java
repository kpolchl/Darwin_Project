//package agh.ics.oop.model.app;
//
//import agh.ics.oop.model.presenter.SimulationPresenter;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class MainApp extends Application {
//
//
////    public void start(Stage primaryStage){
////        primaryStage.show();
////    }
//    public void start(Stage primaryStage) throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getClassLoader().getResource("simulationParameters.fxml"));
//        BorderPane viewRoot = loader.load();
//        SimulationPresenter presenter = loader.getController();
//        configureStage(primaryStage, viewRoot);
//
//        primaryStage.show();
//    }
//    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
//        var scene = new Scene(viewRoot);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Simulation app");
//        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
//        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
//    }
//}


package agh.ics.oop.model.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Ładowanie pierwszego okna
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
