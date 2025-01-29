package agh.ics.oop.model.worldObjects.animal;

import agh.ics.oop.model.exceptions.BreedignError;

import java.util.List;
import java.util.Random;

public class Breeding {
    private final int EnergyPartition;
    private final int BreedingEnergy;
    private final GenomGenerator genomGenerator = new GenomGenerator();

    public Breeding(int EnergyPartition, int BreedingEnergy) {
        this.EnergyPartition = EnergyPartition;
        this.BreedingEnergy = BreedingEnergy;
    }

    public boolean canBreed(Animal animal) {
        return animal.getEnergy() > BreedingEnergy;
    }

    public int calculateEnergyBreedingLoss(Animal animal) {
        return (animal.getEnergy() - 1) / EnergyPartition + 1; // custom ceil function found on stack overflow
    }

    private int energyAfterBreeding(Animal animal) {
        return animal.getEnergy() - calculateEnergyBreedingLoss(animal);
    }


    public Animal breed(Animal father, Animal mother) throws BreedignError {
        if (canBreed(father) && canBreed(mother)) {
            int newEnergy = calculateEnergyBreedingLoss(father) + calculateEnergyBreedingLoss(mother);
            father.setEnergy(energyAfterBreeding(father));
            mother.setEnergy(energyAfterBreeding(mother));

            List<Integer> newGenome = genomGenerator.generateGenomeByMating(father.getGenome(), mother.getGenome(), father.getEnergy(), mother.getEnergy());

            Animal child = new Animal(father.getPosition(), newEnergy, newGenome);

            father.addChild(child);
            mother.addChild(child);

            return child;
        }
        throw new BreedignError(father, mother);
    }

}
