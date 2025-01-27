package agh.ics.oop.records;

import agh.ics.oop.model.utils.Vector2d;

public record WorldConfiguration(Vector2d maxVector, int plantStartingNumber, int plantDaily, int plantEnergy,
                                 int animalStartingNumber, int animalStartingEnergy, int animalBreedingEnergyLoss,
                                 int animalEnergyPartition, int animalEnergyToReproduce, int animalMutationMinimum,
                                 int animalMutationMaximum, int animalGenomeLength, boolean mapType, boolean mutationType
                                 ) {
}
