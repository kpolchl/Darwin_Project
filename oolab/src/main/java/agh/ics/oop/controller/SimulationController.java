package agh.ics.oop.controller;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldMap.AbstractWorldMap;
import agh.ics.oop.records.WorldConfiguration;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationController {
    @FXML
    private GridPane mapGrid;

    private WorldConfiguration worldConfiguration;
    private AbstractWorldMap worldMap;

    private static int WIDTH;
    private static int HEIGHT;
    private int xMax;
    private int yMax;
    private int xMin;
    private int yMin;
    private static final int CELL_WIDTH = 30;
    private static final int CELL_HEIGHT = 30;

    public void setWorldConfiguration(WorldConfiguration worldConfiguration) {
        this.worldConfiguration = worldConfiguration;
        initialize();
    }

    public void initialize() {
        if (worldConfiguration != null) {
            // Initialize grid dimensions based on world configuration
            Vector2d maxVector = worldConfiguration.maxVector();
            xMax = maxVector.getX();
            yMax = maxVector.getY();
            xMin = 0;
            yMin = 0;
            WIDTH = xMax - xMin + 1;
            HEIGHT = yMax - yMin + 1;

            // Initialize grid layout
            initializeGrid();
            drawMap();
        }
    }

    private void initializeGrid() {
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();

        // Add column constraints
        for (int i = 0; i <= WIDTH; i++) {
            ColumnConstraints column = new ColumnConstraints(CELL_WIDTH);
            mapGrid.getColumnConstraints().add(column);
        }

        // Add row constraints
        for (int i = 0; i <= HEIGHT; i++) {
            RowConstraints row = new RowConstraints(CELL_HEIGHT);
            mapGrid.getRowConstraints().add(row);
        }
    }

    public void xyLabel() {
        Label label = new Label("y/x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    public void drawRows() {
        for (int i = 0; i < HEIGHT; i++) {
            Label label = new Label(Integer.toString(yMax - i));
            mapGrid.add(label, 0, i + 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    public void drawColumns() {
        for (int i = 0; i < WIDTH; i++) {
            Label label = new Label(Integer.toString(xMin + i));
            mapGrid.add(label, i + 1, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

//    public void addElements() {
//        for (int i = xMin; i <= xMax; i++) {
//            for (int j = yMax; j >= yMin; j--) {
//                Vector2d pos = new Vector2d(i, j);
//                Node element;
//
//                if (worldMap.isOccupied(pos)) {
//                    element = new Label(worldMap.objectAt(pos).toString());
//                } else {
//                    element = new Label(" ");
//                }
//
//                mapGrid.add(element, i - xMin + 1, yMax - j + 1);
//                GridPane.setHalignment(element, HPos.CENTER);
//            }
//        }
//    }



    public void drawMap() {
        xyLabel();
        drawRows();
        drawColumns();
//        addElements();
        mapGrid.setGridLinesVisible(true);
    }

    // Other methods (xyLabel, drawRows, drawColumns, addElements, clearGrid, mapChanged) remain the same

    public static void openSimulationWindow(WorldConfiguration worldConfiguration) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(SimulationController.class.getResource("/simulationRunning.fxml"));
                Parent root = loader.load();

                // Get the controller and set the world configuration
                SimulationController controller = loader.getController();
                controller.setWorldConfiguration(worldConfiguration);

                Stage stage = new Stage();
                stage.setTitle("Simulation Running");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}