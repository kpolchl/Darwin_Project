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
//    energia konieczna, by uznać zwierzaka za najedzonego (i gotowego do rozmnażania),
//    energia rodziców zużywana by stworzyć potomka,
//    minimalna i maksymalna liczba mutacji u potomków (może być równa 0),
//    wariant mutacji (wyjaśnione w sekcji poniżej),
//    długość genomu zwierzaków, da
//    wariant zachowania zwierzaków (wyjaśnione w sekcji poniżej) da
    private final AbstractWorldMap worldMap;
    private final WorldConfiguration worldConfiguration;
    private boolean running = true;

    public Simulation(WorldConfiguration worldConfiguration) {
        this.worldConfiguration = worldConfiguration;

        this.worldMap = worldConfiguration.mapType() ?
                new EquatorMap(worldConfiguration.maxVector()) :
                new CrawlingJungleWorld(worldConfiguration.maxVector());

        worldMap.createStartingAnimals(worldConfiguration.animalStartingNumber() , worldConfiguration.animalStartingEnergy() , worldConfiguration.animalGenomeLength());

        worldMap.plantGrow(worldConfiguration.plantStartingNumber());


    }




    public void run() {
        while (running) {
            worldMap.deleteDeadAnimals();

        }


    }

}
