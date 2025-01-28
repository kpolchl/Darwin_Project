package agh.ics.oop.controller;

import agh.ics.oop.Simulation;
import agh.ics.oop.Stats;
import agh.ics.oop.model.observators.MapChangeListener;
import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldMap.AbstractWorldMap;
import agh.ics.oop.model.worldObjects.WorldElement;
import agh.ics.oop.records.WorldConfiguration;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class SimulationController implements MapChangeListener {

    @FXML
    private Label plantCountLabel;
    @FXML
    private Label freeSpaceCountLabel;
    @FXML
    private Label averageLifeSpanLabel;
    @FXML
    private Label averageLivingEnergyLabel;
    @FXML
    private Label averageChildrenCountLabel;
    @FXML
    private Label trackedAnimalLabel;
    @FXML
    private Label animalCountLabel;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label statsLabel;

    private Simulation simulation;
    private Thread simulationThread;
    private final int CELL_WIDTH = 30;
    private final int CELL_HEIGHT = 30;
    private WorldConfiguration worldConfiguration;

    public void initialize(WorldConfiguration config) {
        this.worldConfiguration = config;
        initializeGrid();
        startSimulation();
    }

    private void initializeGrid() {
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();

        // Add column constraints (including header column)
        for (int i = 0; i <= worldConfiguration.maxVector().getX() + 1; i++) {
            ColumnConstraints column = new ColumnConstraints(CELL_WIDTH);
            mapGrid.getColumnConstraints().add(column);
        }

        // Add row constraints (including header row)
        for (int i = 0; i <= worldConfiguration.maxVector().getY() + 1; i++) {
            RowConstraints row = new RowConstraints(CELL_HEIGHT);
            mapGrid.getRowConstraints().add(row);
        }

        mapGrid.setGridLinesVisible(true);
    }

    private void startSimulation() {
        simulation = new Simulation(worldConfiguration, this);
        simulationThread = new Thread(simulation);
        simulationThread.start();
    }

    public void stopSimulation() {
        if (simulation != null) {
            simulation.stop();
        }
        if (simulationThread != null) {
            simulationThread.interrupt();
        }
    }

    public void updateMap(AbstractWorldMap worldMap) {
        clearGrid();
        drawGrid();
        addElements(worldMap);
    }

    private void clearGrid() {
        mapGrid.getChildren().clear();
        mapGrid.setGridLinesVisible(false);
        mapGrid.setGridLinesVisible(true);
    }

    private void drawGrid() {
        // Add corner label (y/x)
        Label cornerLabel = new Label("y/x");
        mapGrid.add(cornerLabel, 0, 0);
        GridPane.setHalignment(cornerLabel, HPos.CENTER);

        // Add column headers (x coordinates)
        for (int x = 0; x <= worldConfiguration.maxVector().getX(); x++) {
            Label label = new Label(Integer.toString(x));
            mapGrid.add(label, x + 1, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        // Add row headers (y coordinates)
        for (int y = 0; y <= worldConfiguration.maxVector().getY(); y++) {
            Label label = new Label(Integer.toString(worldConfiguration.maxVector().getY() - y));
            mapGrid.add(label, 0, y + 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    private void addElements(AbstractWorldMap worldMap) {
        for (int x = 0; x <= worldConfiguration.maxVector().getX(); x++) {
            for (int y = 0; y <= worldConfiguration.maxVector().getY(); y++) {
                Vector2d pos = new Vector2d(x, y);
                WorldElement element = worldMap.objectAt(pos);

                Label label = new Label(element != null ? element.toString() : " ");
                mapGrid.add(label, x + 1, worldConfiguration.maxVector().getY() - y + 1);
                GridPane.setHalignment(label, HPos.CENTER);
            }
        }
    }

    @Override
    public void mapChanged(AbstractWorldMap worldMap, Stats stats) {
        Platform.runLater(() -> {
            updateMap(worldMap);
            updateStats(stats);
        });
    }

    private void updateStats(Stats stats) {
        animalCountLabel.setText(String.valueOf(stats.getAnimalCount()));
        plantCountLabel.setText(String.valueOf(stats.getPlantCount()));
        freeSpaceCountLabel.setText(String.valueOf(stats.getFreeSpaceCount()));
        averageLivingEnergyLabel.setText(String.valueOf(stats.getAvgLivingEnergy()));
        averageLifeSpanLabel.setText(String.valueOf(stats.getAvgLifespan()));
        averageChildrenCountLabel.setText(String.valueOf(stats.getAvgChildren()));
        trackedAnimalLabel.setText(String.valueOf(stats.getPlantCount()));

    }

    public static void openNewSimulation(WorldConfiguration config) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(SimulationController.class.getResource("/simulationRunning.fxml"));
                Parent root = loader.load();

                SimulationController controller = loader.getController();
                controller.initialize(config);

                Stage stage = new Stage();
                stage.setTitle("Simulation " + System.currentTimeMillis());
                stage.setScene(new Scene(root));
                stage.show();

                stage.setOnCloseRequest(event -> controller.stopSimulation());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}