package agh.ics.oop.model.presenter;


import agh.ics.oop.Simulation;
import agh.ics.oop.model.observators.MapChangeListener;
import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldMap.AbstractWorldMap;
import agh.ics.oop.model.worldMap.EquatorMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {

    @FXML
    private Label infoLabel;

    @FXML
    private Label changeData;

    @FXML
    private Button start;

    @FXML
    private TextField input;

    @FXML
    private GridPane mapGrid;


    private AbstractWorldMap worldMap;

    private static int WIDTH;
    private static int HEIGHT;
    private int xMax;
    private int yMax;
    private int xMin;
    private int yMin;
    private static int CELL_WIDTH = 30;
    private static int CELL_HEIGHT = 30;

    public void setWorldMap(AbstractWorldMap map) {
        this.worldMap = map;
    }


    public void xyLabel(){
        Label label = new Label("y/x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
    }

    public void drawRows() { // first row numbers
        for (int i = 0; i < HEIGHT; i++) {
            if (mapGrid.getRowConstraints().size() <= i + 1) {
                mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
            }
            Label label = new Label(Integer.toString(yMax - i));
            mapGrid.add(label, 0, i + 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    public void drawColumns() { // first column numbers
        for (int i = 0; i < WIDTH; i++) {
            if (mapGrid.getColumnConstraints().size() <= i + 1) {
                mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            }
            Label label = new Label(Integer.toString(xMin + i));
            mapGrid.add(label, i + 1, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }



    public void addElements(){
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                Vector2d pos = new Vector2d(i, j);
                Node element;

                if (worldMap.isOccupied(pos)) {
                    element = new Label(worldMap.objectAt(pos).toString());
                } else {
                    element = new Label(" ");
                }

                mapGrid.add(element, i - xMin + 1, yMax - j + 1);
                mapGrid.setHalignment(element, HPos.CENTER);
            }
        }
    }



    public void drawMap(){
        xyLabel();
        drawRows();
        drawColumns();
        addElements();
        mapGrid.setGridLinesVisible(true);
    }

    @Override
    public void mapChanged(AbstractWorldMap worldMap, String message) {
        Platform.runLater(() -> {
            clearGrid();
            drawMap();
            changeData.setText(message);
        });
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
    public void onSimulationStartClicked(ActionEvent actionEvent) {  // javafx acction event
        new Thread(() -> {
            try {
                String userInput = input.getText();
//                AbstractWorldMap recWorld =  new EquatorMap();

//                Simulation Simulation = new Simulation();
//                SimulationEngine simulationEngine = new SimulationEngine(List.of(recSimulation));
//                recWorld.addObserver(this); //add observer
//                this.setWorldMap(recWorld);
//                simulationEngine.runSync(); // run simulations
            } catch (IllegalArgumentException e) {
                Platform.runLater(() -> changeData.setText("Invalid input: " + e.getMessage()));
            }
        }).start();
    }

}

