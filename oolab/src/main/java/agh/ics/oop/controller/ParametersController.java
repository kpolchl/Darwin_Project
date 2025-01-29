package agh.ics.oop.controller;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.records.WorldConfiguration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Properties;

public class ParametersController {

    @FXML
    private CheckBox saveToCSVCheckBox;
    @FXML
    private TextField energyDepletion;
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

    private static final String DEFAULT_DIRECTORY = "simulation_parameters";
    private Stage stage;

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
            int energyDepletionValue = Integer.parseInt(energyDepletion.getText());
            boolean saveToCSV = saveToCSVCheckBox.isSelected();

            // Get selected values from ChoiceBox components
            boolean mapTypeValue = mapVariant.getValue().equals("Equator");
            boolean mutationTypeValue = mutationVariant.getValue().equals("Random");

            // Validate input values
            if (mapWidthValue <= 0 || mapHeightValue <= 0 || plantStartingNumberValue < 0 || dailyPlantGrowthValue < 0 ||
                    plantEnergyValue <= 0 || animalStartingEnergyValue <= 0 || energyPartitionValue <= 0 ||
                    initialAnimalsValue <= 0 || reproductionEnergyValue <= 0 || maxMutationsValue < 0 || genomeLengthValue <= 0 || minMutationsValue < 0) {
                throw new IllegalArgumentException("All numeric values must be positive.");
            }
            if (minMutationsValue > maxMutationsValue) {
                throw new IllegalArgumentException("Minimum mutations value must be less than maximum mutations value.");
            }
            if((mapWidthValue * mapHeightValue) < initialAnimalsValue) {
                throw new IllegalArgumentException("Animal number should be smaller than map.");
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
                    mutationTypeValue,
                    energyDepletionValue,
                    saveToCSV

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
            SimulationController.openNewSimulation(worldConfiguration);
        } catch (IllegalArgumentException e) {
            // Show an error message if validation fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void onLoadData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Simulation Parameters");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Properties Files", "*.properties"));

        File defaultDirectory = new File(DEFAULT_DIRECTORY);
        if (defaultDirectory.exists() && defaultDirectory.isDirectory()) {
            fileChooser.setInitialDirectory(defaultDirectory);
        }
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try (InputStream input = new FileInputStream(file)) {
                Properties properties = new Properties();
                properties.load(input);

                // Load data into the UI components
                mapWidth.setText(properties.getProperty("mapWidth"));
                mapHeight.setText(properties.getProperty("mapHeight"));
                mapVariant.setValue(properties.getProperty("mapVariant"));
                initialAnimals.setText(properties.getProperty("initialAnimals"));
                animalStartingEnergy.setText(properties.getProperty("animalStartingEnergy"));
                reproductionEnergy.setText(properties.getProperty("reproductionEnergy"));
                energyPartition.setText(properties.getProperty("energyPartition"));
                energyDepletion.setText(properties.getProperty("energyDepletion"));
                plantEnergy.setText(properties.getProperty("plantEnergy"));
                plantStartingNumber.setText(properties.getProperty("plantStartingNumber"));
                dailyPlantGrowth.setText(properties.getProperty("dailyPlantGrowth"));
                mutationVariant.setValue(properties.getProperty("mutationVariant"));
                minMutations.setText(properties.getProperty("minMutations"));
                maxMutations.setText(properties.getProperty("maxMutations"));
                genomeLength.setText(properties.getProperty("genomeLength"));

            } catch (IOException e) {
                showErrorDialog("Error loading file: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onSaveData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Simulation Parameters");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Properties Files", "*.properties"));
        File defaultDirectory = new File(DEFAULT_DIRECTORY);
        if (defaultDirectory.exists() && defaultDirectory.isDirectory()) {
            fileChooser.setInitialDirectory(defaultDirectory);
        }
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (OutputStream output = new FileOutputStream(file)) {
                Properties properties = new Properties();

                // Save data from the UI components
                properties.setProperty("mapWidth", mapWidth.getText());
                properties.setProperty("mapHeight", mapHeight.getText());
                properties.setProperty("mapVariant", mapVariant.getValue());
                properties.setProperty("initialAnimals", initialAnimals.getText());
                properties.setProperty("animalStartingEnergy", animalStartingEnergy.getText());
                properties.setProperty("reproductionEnergy", reproductionEnergy.getText());
                properties.setProperty("energyPartition", energyPartition.getText());
                properties.setProperty("energyDepletion", energyDepletion.getText());
                properties.setProperty("plantEnergy", plantEnergy.getText());
                properties.setProperty("plantStartingNumber", plantStartingNumber.getText());
                properties.setProperty("dailyPlantGrowth", dailyPlantGrowth.getText());
                properties.setProperty("mutationVariant", mutationVariant.getValue());
                properties.setProperty("minMutations", minMutations.getText());
                properties.setProperty("maxMutations", maxMutations.getText());
                properties.setProperty("genomeLength", genomeLength.getText());

                properties.store(output, "Simulation Parameters");

            } catch (IOException e) {
                showErrorDialog("Error saving file: " + e.getMessage());
            }
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}