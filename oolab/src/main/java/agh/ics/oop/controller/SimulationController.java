package agh.ics.oop.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class SimulationController {


    public void startSimulation(ActionEvent event) {
        System.out.println("Simulation started!");
        // Tutaj możesz zaimplementować logikę rozpoczęcia symulacji
    }

    public static void openSimulationWindow() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(SimulationController.class.getResource("/simulationRunning.fxml"));
                Parent root = loader.load();
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