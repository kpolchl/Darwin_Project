package agh.ics.oop.controller;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.records.WorldConfiguration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ParametersController {

    @FXML
    private TextField energyPartition;
    @FXML
    private TextField animalStartingEnergy;
    @FXML
    private TextField plantStartingNumber;
    @FXML
    private TextField mapWidth;
    @FXML
    private TextField mapHeight;
    @FXML
    private ChoiceBox<String> mapVariant;
    @FXML
    private TextField initialAnimals;
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
    private WorldConfiguration getWorldConfiguration() throws IllegalArgumentException {
        try {
            // Parse values from UI components
            int mapWidthValue = Integer.parseInt(mapWidth.getText());
            int mapHeightValue = Integer.parseInt(mapHeight.getText());
            int plantStartingNumberValue = Integer.parseInt(plantStartingNumber.getText());
            int dailyPlantGrowthValue = Integer.parseInt(dailyPlantGrowth.getText());
            int plantEnergyValue = Integer.parseInt(plantEnergy.getText());
            int animalStartingEnergyValue = Integer.parseInt(animalStartingEnergy.getText());
            int energyPartitionValue = Integer.parseInt(energyPartition.getText());
            int initialAnimalsValue = Integer.parseInt(initialAnimals.getText());
            int reproductionEnergyValue = Integer.parseInt(reproductionEnergy.getText());
            int minMutationsValue = Integer.parseInt(minMutations.getText());
            int maxMutationsValue = Integer.parseInt(maxMutations.getText());
            int genomeLengthValue = Integer.parseInt(genomeLength.getText());

            // Get selected values from ChoiceBox components
            boolean mapTypeValue = mapVariant.getValue().equals("Equator");
            boolean mutationTypeValue = mutationVariant.getValue().equals("Random");

            // Validate input values
            if (mapWidthValue <= 0 || mapHeightValue <= 0 || plantStartingNumberValue < 0 || dailyPlantGrowthValue < 0 ||
                    plantEnergyValue <= 0 || animalStartingEnergyValue <= 0 || energyPartitionValue <= 0 ||
                    initialAnimalsValue <= 0 || reproductionEnergyValue <= 0 || minMutationsValue < 0 ||
                    maxMutationsValue < 0 || genomeLengthValue <= 0) {
                throw new IllegalArgumentException("All numeric values must be positive.");
            }

            // Create a Vector2d for the map dimensions
            Vector2d maxVector = new Vector2d(mapWidthValue, mapHeightValue);

            // Create and return a new WorldConfiguration object
            return new WorldConfiguration(
                    maxVector,
                    plantStartingNumberValue,
                    dailyPlantGrowthValue,
                    plantEnergyValue,
                    initialAnimalsValue,
                    animalStartingEnergyValue,
                    energyPartitionValue,
                    reproductionEnergyValue,
                    minMutationsValue,
                    maxMutationsValue,
                    genomeLengthValue,
                    mapTypeValue,
                    mutationTypeValue
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input: All fields must contain numeric values.");
        }
    }

    public void onStartSimulation(ActionEvent actionEvent) {
        try {
            // Get the configuration from the form
            WorldConfiguration worldConfiguration = getWorldConfiguration();

            // Open the simulation window and pass the configuration
            SimulationController.openSimulationWindow(worldConfiguration);
        } catch (IllegalArgumentException e) {
            // Show an error message if validation fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}