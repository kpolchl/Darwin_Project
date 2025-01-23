package agh.ics.oop.model;

import agh.ics.oop.model.animal.Animal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Testuj {
    protected Vector2d MIN_COORD;
    protected Vector2d MAX_COORD;
    protected HashMap<Vector2d, List<Animal>> AnimalMap; // to track moves and breeding
    protected List<Animal> Animals;  // list of all animals on the map should be sorted by energy
    protected List<Animal> Children;
    protected HashMap<Vector2d,Grass> GrassMap;

    public Testuj(Vector2d MAX_COORD, List<Animal> animals) {
        this.MIN_COORD = new Vector2d(0,0);
        this.MAX_COORD = MAX_COORD;
        this.AnimalMap = new HashMap<>();
        for (Animal animal : animals) {
            // Use computeIfAbsent to ensure a list is created if the position doesn't exist
            AnimalMap.computeIfAbsent(animal.getPosition(),k -> new ArrayList<>()).add(animal);
        }
    }
}
