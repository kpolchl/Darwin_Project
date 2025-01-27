package agh.ics.oop;

import agh.ics.oop.model.worldMap.AbstractWorldMap;
import agh.ics.oop.model.worldMap.CrawlingJungleWorld;
import agh.ics.oop.model.worldMap.EquatorMap;
import agh.ics.oop.records.WorldConfiguration;

public class Simulation {
    //    wysokość i szerokość mapy, da
//    wariant mapy (wyjaśnione w sekcji poniżej), da
//    startowa liczba roślin, da
//    energia zapewniana przez zjedzenie jednej rośliny,
//    liczba roślin wyrastająca każdego dnia,
//    wariant wzrostu roślin (wyjaśnione w sekcji poniżej), da
//    startowa liczba zwierzaków, da
//    startowa energia zwierzaków,  da
//    energia konieczna, by uznać zwierzaka za najedzonego (i gotowego do rozmnażania),niet
//    energia rodziców zużywana by stworzyć potomka, da
//    minimalna i maksymalna liczba mutacji u potomków (może być równa 0), da
//    wariant mutacji (wyjaśnione w sekcji poniżej), da
//    długość genomu zwierzaków, da
//    wariant zachowania zwierzaków (wyjaśnione w sekcji poniżej) da
    private final AbstractWorldMap worldMap;
    private final WorldConfiguration worldConfiguration;
    private boolean running = true;
    private int dayCount = 0;
    private Stats statistics = new Stats();

    public Simulation(WorldConfiguration worldConfiguration) {
        this.worldConfiguration = worldConfiguration;

        //map type
        //map size, energy partition , breedingEnergyLoss
        this.worldMap = worldConfiguration.mapType() ?
                new EquatorMap(worldConfiguration.maxVector(), worldConfiguration.animalEnergyPartition(), worldConfiguration.animalBreedingEnergyLoss()) :
                new CrawlingJungleWorld(worldConfiguration.maxVector(), worldConfiguration.animalEnergyPartition(), worldConfiguration.animalBreedingEnergyLoss());

        // starting animal numbers
        worldMap.createStartingAnimals(worldConfiguration.animalStartingNumber() , worldConfiguration.animalStartingEnergy() , worldConfiguration.animalGenomeLength());

        //starting plants
        worldMap.plantGrow(worldConfiguration.plantStartingNumber());

    }




    public void run() {
        while (running) {
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
