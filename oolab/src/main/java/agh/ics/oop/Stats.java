package agh.ics.oop;

//Program ma pozwalać na śledzenie następujących statystyk dla aktualnej sytuacji w symulacji:\
//
//
//
//
//
//
//


//Po zatrzymaniu programu można oznaczyć jednego zwierzaka jako wybranego do śledzenia. Od tego momentu (do zatrzymania śledzenia) UI powinien przekazywać nam informacje o jego statusie i historii:

//jaki ma genom,
//która jego część jest aktywowana,
//ile ma energii,
//ile zjadł roślin,XXX
//ile posiada dzieci,XXX
//ile posiada potomków (niekoniecznie będących bezpośrednio dziećmi),XXX
//ile dni już żyje (jeżeli żyje),
//którego dnia zmarło (jeżeli żywot już skończyło).


//Po zatrzymaniu programu powinno być też możliwe:

//pokazanie, które ze zwierząt mają dominujący (najpopularniejszy) genotyp (np. poprzez wyróżnienie ich wizualnie),
//pokazanie, które z pól są preferowane przez rośliny (np. poprzez wyróżnienie ich wizualnie).


import java.util.ArrayList;
import java.util.List;

public class Stats {
    private int animalCount = 0;
    private int plantCount = 0;
    private int freeSpaceCount = 0;
    private List<Integer> mostPopularGenotype = new ArrayList<>();
    private double avgLivingEnergy = 0.0;
    private double avgLifespan = 0.0;
    private double avgChildren = 0.0;
    private int dayCount = 0;


    public int getAnimalCount() {
        return animalCount;
    }

    public int getPlantCount() {
        return plantCount;
    }

    public int getFreeSpaceCount() {
        return freeSpaceCount;
    }

    public List<Integer> getMostPopularGenotype() {
        return mostPopularGenotype;
    }

    public double getAvgLivingEnergy() {
        return avgLivingEnergy;
    }

    public double getAvgLifespan() {
        return avgLifespan;
    }

    public double getAvgChildren() {
        return avgChildren;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setStats(int animalCount, int plantCount, int freeSpaceCount, int dayCount, double avgLivingEnergy, double avgLifespan, double avgChildren, List<Integer> mostPopularGenotypes) {
        this.animalCount = animalCount;
        this.plantCount = plantCount;
        this.freeSpaceCount = freeSpaceCount;
        this.mostPopularGenotype = mostPopularGenotypes;
        this.avgLivingEnergy = avgLivingEnergy;
        this.avgLifespan = avgLifespan;
        this.avgChildren = avgChildren;
        this.dayCount = dayCount;

    }
}
