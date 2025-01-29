package agh.ics.oop.model.utils;

import java.util.ArrayList;
import java.util.List;

public class Stats {

    private int animalCount = 0;
    private int plantCount = 0;
    private int freeSpaceCount = 0;
    private double avgLivingEnergy = 0.0;
    private double avgLifespan = 0.0;
    private double avgChildren = 0.0;
    private int dayCount = 0;
    private List<Integer> topOneGenome = new ArrayList<>();
    private List<Integer> topTwoGenome = new ArrayList<>();
    private List<Integer> topThreeGenome = new ArrayList<>();

    public List<Integer> getTopOneGenome() {
        return topOneGenome;
    }

    public List<Integer> getTopTwoGenome() {
        return topTwoGenome;
    }

    public List<Integer> getTopThreeGenome() {
        return topThreeGenome;
    }

    public int getAnimalCount() {
        return animalCount;
    }

    public int getPlantCount() {
        return plantCount;
    }

    public int getFreeSpaceCount() {
        return freeSpaceCount;
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

    public void setStats(int animalCount, int plantCount, int freeSpaceCount, int dayCount, double avgLivingEnergy, double avgLifespan, double avgChildren, List<List<Integer>> topGenomes) {
        this.animalCount = animalCount;
        this.plantCount = plantCount;
        this.freeSpaceCount = freeSpaceCount;
        this.avgLivingEnergy = avgLivingEnergy;
        this.avgLifespan = avgLifespan;
        this.avgChildren = avgChildren;
        this.dayCount = dayCount;

        if (!topGenomes.isEmpty()) this.topOneGenome = topGenomes.get(0);
        if (topGenomes.size() > 1) this.topTwoGenome = topGenomes.get(1);
        if (topGenomes.size() > 2) this.topThreeGenome = topGenomes.get(2);
    }

}
