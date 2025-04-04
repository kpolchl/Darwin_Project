package agh.ics.oop;

import agh.ics.oop.controller.SimulationController;

import agh.ics.oop.model.utils.Stats;
import agh.ics.oop.model.worldMap.AbstractWorldMap;
import agh.ics.oop.model.worldMap.CrawlingJungleMap;
import agh.ics.oop.model.worldMap.EquatorMap;
import agh.ics.oop.records.WorldConfiguration;


public class Simulation implements Runnable {
    private final AbstractWorldMap worldMap;
    private final WorldConfiguration worldConfiguration;
    private boolean running = true;
    private int dayCount = 0;
    private Stats statistics = new Stats();
    private SimulationController controller;

    public int getDayCount() {
        return dayCount;
    }

    public Simulation(WorldConfiguration worldConfiguration, SimulationController controller) {
        this.worldConfiguration = worldConfiguration;
        this.controller = controller;
        this.worldMap = worldConfiguration.mapType() ? new EquatorMap(worldConfiguration.maxVector(), worldConfiguration.animalEnergyPartition(), worldConfiguration.animalEnergyToReproduce()) : new CrawlingJungleMap(worldConfiguration.maxVector(), worldConfiguration.animalEnergyPartition(), worldConfiguration.animalEnergyToReproduce());

        worldMap.createStartingAnimals(worldConfiguration.animalStartingNumber(), worldConfiguration.animalStartingEnergy(), worldConfiguration.animalGenomeLength());
        worldMap.plantGrow(worldConfiguration.plantStartingNumber());
        worldMap.addObserver(controller);
    }

    @Override
    public void run() {
        while (running) {
            worldMap.deleteDeadAnimals(dayCount);
            worldMap.animalDay(worldConfiguration.plantEnergy(), worldConfiguration.animalMutationMinimum(), worldConfiguration.animalMutationMaximum(), worldConfiguration.energyDeplation(), worldConfiguration.mutationType());
            worldMap.plantGrow(worldConfiguration.plantDaily());
            dayCount++;
            worldMap.setStatistics(statistics, dayCount);

            worldMap.mapChanged(statistics);


            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void start() {
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }

    public AbstractWorldMap getWorldMap() {
        return worldMap;
    }
}