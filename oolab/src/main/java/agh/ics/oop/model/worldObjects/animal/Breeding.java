package agh.ics.oop.model.worldObjects.animal;

import agh.ics.oop.model.exceptions.BreedignError;

import java.util.List;
import java.util.Random;

public class Breeding {
    private final int ENERGY_PARTITION;
    private final int BREEDING_ENERGY;
    private GenomGenerator genomGenerator = new GenomGenerator();
    private Mutations mutations = new Mutations();

    public Breeding(int ENERGY_PARTITION ,int BREEDING_ENERGY) {
        this.ENERGY_PARTITION = ENERGY_PARTITION;
        this.BREEDING_ENERGY = BREEDING_ENERGY;
    }

    private boolean canBreed(Animal animal) {
        return animal.getEnergy()>BREEDING_ENERGY;
    }

    public int calculateEnergyBreedingLoss(Animal animal) {
        return (animal.getEnergy()-1)/ENERGY_PARTITION+1; // custom ceil function found on stack overflow
    }
    private int energyAfterBreeding(Animal animal) {
        return animal.getEnergy() - calculateEnergyBreedingLoss(animal);
    }


    public Animal breed(Animal father, Animal mother) throws BreedignError {
        Random rand = new Random();
        if (canBreed(father) && canBreed(mother) ) {
            int newEnergy = calculateEnergyBreedingLoss(father) + calculateEnergyBreedingLoss(mother);
            father.setEnergy(energyAfterBreeding(father));
            mother.setEnergy(energyAfterBreeding(mother));

            List<Integer> newGenome = genomGenerator.generateGenomeByMating(father.getGenome() ,mother.getGenome() ,father.getEnergy() ,mother.getEnergy());

            return new Animal(father.getPosition(), newEnergy, newGenome);
        }
        throw new BreedignError(father, mother);
    }
}
