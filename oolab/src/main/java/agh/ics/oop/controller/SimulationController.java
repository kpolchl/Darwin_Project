package agh.ics.oop.controller;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.utils.Stats;
import agh.ics.oop.model.observators.MapChangeListener;
import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldMap.AbstractWorldMap;
import agh.ics.oop.model.worldObjects.WorldElement;
import agh.ics.oop.model.worldObjects.animal.Animal;
import agh.ics.oop.records.WorldConfiguration;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class SimulationController implements MapChangeListener {

    @FXML
    private Label topOneGenomeLabel;
    @FXML
    private Label topTwoGenomeLabel;
    @FXML
    private Label topThreeGenomeLabel;
    @FXML
    private Label dayCount;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
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
    private Label animalCountLabel;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label trackedAnimalGenome;
    @FXML
    private Label trackedAnimalActiveGene;
    @FXML
    private Label trackedAnimalEnergy;
    @FXML
    private Label trackedAnimalPlantsEaten;
    @FXML
    private Label trackedAnimalChildren;
    @FXML
    private Label trackedAnimalDescendants;
    @FXML
    private Label trackedAnimalAge;
    @FXML
    private Button stopTrackingButton;


    private final int CELL_WIDTH = 30;
    private final int CELL_HEIGHT = 30;

    private Animal trackedAnimal;
    private Simulation simulation;
    private Thread simulationThread;
    private WorldConfiguration worldConfiguration;
    private boolean isRunning = false;

    public void startTracking(Animal animal) {
        if (!isRunning) {
            trackedAnimal = animal;
            stopTrackingButton.setDisable(false);
            updateTrackingDisplay();
        }
    }

    public void stopTracking() {
        clearTrackingDisplay();
        trackedAnimal = null;
        stopTrackingButton.setDisable(true);
    }

    private void updateTrackingDisplay() {
        if (trackedAnimal == null) {
            clearTrackingDisplay();
            return;
        }

        trackedAnimalGenome.setText("Genome: " + trackedAnimal.getGenome());
        trackedAnimalActiveGene.setText("Active Gene: " + trackedAnimal.getActiveGene());
        trackedAnimalEnergy.setText("Energy: " + trackedAnimal.getEnergy());
        trackedAnimalPlantsEaten.setText("Plants Eaten: " + trackedAnimal.getPlantEaten());
        trackedAnimalChildren.setText("Children: " + trackedAnimal.getNumberOfChildren());
        trackedAnimalDescendants.setText("Descendants: " + trackedAnimal.getNumberOfDescendants());

        if (!trackedAnimal.isDead()) {
            trackedAnimalAge.setText("Age: " + trackedAnimal.getAge() + " days");
        } else {
            trackedAnimalAge.setText("Died on day: " + trackedAnimal.getDayOfDeath());
        }
    }

    private void clearTrackingDisplay() {
        trackedAnimalGenome.setText("No animal tracked");
        trackedAnimalActiveGene.setText("");
        trackedAnimalEnergy.setText("");
        trackedAnimalPlantsEaten.setText("");
        trackedAnimalChildren.setText("");
        trackedAnimalDescendants.setText("");
        trackedAnimalAge.setText("");
    }

    public void initialize(WorldConfiguration config) {
        this.worldConfiguration = config;
        initializeGrid();
        initializeButtons();
        startSimulation();
    }

    private void initializeButtons() {
        stopButton.setOnAction(event -> stopSimulation());
        startButton.setOnAction(event -> resumeSimulation());

        stopButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        stopTrackingButton.setOnAction(event -> stopTracking());
        stopTrackingButton.setDisable(true);
    }

    private void startSimulation() {
        if (!isRunning) {
            if (simulation == null) {
                simulation = new Simulation(worldConfiguration, this);
                simulationThread = new Thread(simulation);
            }
            simulationThread.start();
            isRunning = true;
            startButton.setDisable(true);
            stopButton.setDisable(false);
        }
    }

    public void stopSimulation() {
        if (isRunning && simulation != null) {
            simulation.stop();
            simulationThread.interrupt();
            isRunning = false;
            startButton.setDisable(false);
            stopButton.setDisable(true);
        }
    }

    public void resumeSimulation() {
        if (!isRunning && simulation != null) {
            simulationThread = new Thread(simulation);
            simulationThread.start();
            isRunning = true;
            simulation.start();
            startButton.setDisable(true);
            stopButton.setDisable(false);
        }
    }

    private void initializeGrid() {
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();

        for (int i = 0; i <= worldConfiguration.maxVector().getX() + 1; i++) {
            ColumnConstraints column = new ColumnConstraints(CELL_WIDTH);
            mapGrid.getColumnConstraints().add(column);
        }

        for (int i = 0; i <= worldConfiguration.maxVector().getY() + 1; i++) {
            RowConstraints row = new RowConstraints(CELL_HEIGHT);
            mapGrid.getRowConstraints().add(row);
        }

        mapGrid.setGridLinesVisible(true);
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
        Label cornerLabel = new Label("y/x");
        mapGrid.add(cornerLabel, 0, 0);
        GridPane.setHalignment(cornerLabel, HPos.CENTER);

        for (int x = 0; x <= worldConfiguration.maxVector().getX(); x++) {
            Label label = new Label(Integer.toString(x));
            mapGrid.add(label, x + 1, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int y = 0; y <= worldConfiguration.maxVector().getY(); y++) {
            Label label = new Label(Integer.toString(worldConfiguration.maxVector().getY() - y));
            mapGrid.add(label, 0, y + 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    private void addElements(AbstractWorldMap worldMap) {
        synchronized (worldMap) {
            for (int x = 0; x <= worldConfiguration.maxVector().getX(); x++) {
                for (int y = 0; y <= worldConfiguration.maxVector().getY(); y++) {
                    Vector2d pos = new Vector2d(x, y);
                    WorldElement element = worldMap.objectAt(pos);
                    Label label = new Label(element != null ? element.toString() : " ");

                    if (element instanceof Animal) {
                        label.setOnMouseClicked(event -> {
                            if (!isRunning) {
                                startTracking((Animal) element);
                            }
                        });

                        if (trackedAnimal != null && element == trackedAnimal) {
                            label.setStyle("-fx-background-color: yellow;");
                        }
                    }

                    mapGrid.add(label, x + 1, worldConfiguration.maxVector().getY() - y + 1);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
            }
        }
    }

    @Override
    public void mapChanged(AbstractWorldMap worldMap, Stats stats) {
        if (!isRunning) return;

        Platform.runLater(() -> {
            updateMap(worldMap);
            updateStats(stats);
            updateTrackingDisplay();
        });
    }

    private void updateStats(Stats stats) {
        dayCount.setText("Day: " + stats.getDayCount());
        animalCountLabel.setText("Animals: " + stats.getAnimalCount());
        plantCountLabel.setText("Plants: " + stats.getPlantCount());
        freeSpaceCountLabel.setText("Free Fields: " + stats.getFreeSpaceCount());
        averageLivingEnergyLabel.setText("Average Energy: " + String.format("%.2f", stats.getAvgLivingEnergy()));
        averageLifeSpanLabel.setText("Average Lifespan: " + String.format("%.2f", stats.getAvgLifespan()));
        averageChildrenCountLabel.setText("Average Children: " + String.format("%.2f", stats.getAvgChildren()));

        topOneGenomeLabel.setText("Top 1 Genome: " + stats.getTopOneGenome());
        topTwoGenomeLabel.setText("Top 2 Genome: " + stats.getTopTwoGenome());
        topThreeGenomeLabel.setText("Top 3 Genome: " + stats.getTopThreeGenome());
    }

    public static void openNewSimulation(WorldConfiguration config) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(SimulationController.class.getResource("/simulationRunning.fxml"));
                Parent root = loader.load();

                SimulationController controller = loader.getController();
                controller.initialize(config);

                Stage stage = new Stage();
                stage.setTitle("Simulation");
                stage.setScene(new Scene(root));
                stage.show();

                stage.setOnCloseRequest(event -> {
                    controller.stopSimulation();
                    stage.close();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}