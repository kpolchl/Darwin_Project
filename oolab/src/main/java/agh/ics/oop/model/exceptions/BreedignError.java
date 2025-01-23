package agh.ics.oop.model.exceptions;

import agh.ics.oop.model.animal.Animal;

public class BreedignError extends RuntimeException {
    public BreedignError(Animal animal1 ,Animal animal2) {
        super("Animals can't breed" + animal1 + " and " + animal2);
    }
}
