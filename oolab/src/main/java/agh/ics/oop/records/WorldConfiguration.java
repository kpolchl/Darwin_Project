package agh.ics.oop.records;

public record WorldConfiguration(int mapHeight, int mapWidth, int plantStartingNumber, int plantDaily, int plantEnergy,
                                 int animalStartingNumber, int animalStartingEnergy, int animalBreedingEnergyLoss,
                                 int animalEnergyLoss, int animalEnergyToReproduce, int animalMutationMinimum,
                                 int animalMutationMaximum, int animalGenotypeLength, boolean variantMap
                                 ) {
}
