package agh.ics.oop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ParametersController {

    public Button startSimulationButton;
    @FXML
    private TextField mapWidth;
    @FXML
    private TextField mapHeight;
    @FXML
    private ChoiceBox<String> mapVariant;
    @FXML
    private TextField initialAnimals;
    @FXML
    private TextField startingEnergy;
    @FXML
    private TextField reproductionEnergy;
    @FXML
    private TextField plantEnergy;
    @FXML
    private TextField dailyPlantGrowth;
    @FXML
    private ChoiceBox<String> mutationVariant;
    @FXML
    private TextField minMutations;
    @FXML
    private TextField maxMutations;
    @FXML
    private TextField genomeLength;

    @FXML
    private void onStartSimulation() {
        try {
            // Pobierz wartości z pól tekstowych
            int width = Integer.parseInt(mapWidth.getText());
            int height = Integer.parseInt(mapHeight.getText());
            String mapType = mapVariant.getValue();
            int animals = Integer.parseInt(initialAnimals.getText());
            int energy = Integer.parseInt(startingEnergy.getText());
            int reproductionThreshold = Integer.parseInt(reproductionEnergy.getText());
            int plantEnergyValue = Integer.parseInt(plantEnergy.getText());
            int dailyGrowth = Integer.parseInt(dailyPlantGrowth.getText());
            String mutationType = mutationVariant.getValue();
            int minMutate = Integer.parseInt(minMutations.getText());
            int maxMutate = Integer.parseInt(maxMutations.getText());
            int genomeLen = Integer.parseInt(genomeLength.getText());
            System.out.println(width + height);

            // Przekazanie parametrów do symulacji
            // TODO: Użyj KLASA1 i KLASA2 do uruchomienia symulacji z tymi parametrami

            // Otwórz nowe okno symulacji
            SimulationController.openSimulationWindow();
        } catch (NumberFormatException e) {
            // Obsługa błędów wprowadzania danych
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values.");
            alert.showAndWait();
        }
    }
}
