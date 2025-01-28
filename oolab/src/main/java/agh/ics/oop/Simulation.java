package agh.ics.oop;

import agh.ics.oop.model.worldMap.AbstractWorldMap;
import agh.ics.oop.model.worldMap.CrawlingJungleWorld;
import agh.ics.oop.model.worldMap.EquatorMap;
import agh.ics.oop.records.WorldConfiguration;

public class Simulation {
    private AbstractWorldMap worldMap;
    private WorldConfiguration worldConfiguration;
    private boolean running = true;
    private int dayCount = 0;
    private Stats statistics = new Stats();

    public Simulation(WorldConfiguration worldConfiguration) {
        this.worldConfiguration = worldConfiguration;
        //map size, energy partition , breedingEnergyLoss
        this.worldMap = worldConfiguration.mapType() ?
                new EquatorMap(worldConfiguration.maxVector(), worldConfiguration.animalEnergyPartition(), worldConfiguration.animalEnergyToReproduce()) :
                new CrawlingJungleWorld(worldConfiguration.maxVector(), worldConfiguration.animalEnergyPartition(), worldConfiguration.animalEnergyToReproduce());

        // starting animal numbers
        worldMap.createStartingAnimals(worldConfiguration.animalStartingNumber(), worldConfiguration.animalStartingEnergy(), worldConfiguration.animalGenomeLength());
        //starting plants
        worldMap.plantGrow(worldConfiguration.plantStartingNumber());
        System.out.println("Simulation started");

    }


    public void run() {
        while (running) {
            System.out.println("simulation day" + dayCount);
            worldMap.deleteDeadAnimals();
            // plantEnergy , mutationMin , mutationMax , mutationType
            worldMap.animalDay(worldConfiguration.plantEnergy(), worldConfiguration.animalMutationMinimum(), worldConfiguration.animalMutationMaximum(), worldConfiguration.mutationType());

            // dailyPlant grow
            worldMap.plantGrow(worldConfiguration.plantDaily());
            dayCount++;
            worldMap.setStatistics(statistics, dayCount);
            try {
                worldMap.mapChanged(statistics);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());

            }
        }
    }

    public void stop() {
        running = false;
    }
}




