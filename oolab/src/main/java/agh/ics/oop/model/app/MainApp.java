import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Tworzenie układu
        VBox root = new VBox();
        root.setSpacing(10); // Odstępy między elementami

        // Ustawienie sceny
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Symulacja świata - UI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
