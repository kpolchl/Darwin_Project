package agh.ics.oop.model.animal;

import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.enums.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Animal {
    private final int STARTING_ENERGY = 100;
    private final int ENERGY_LOSS=2;

    private final Mutations mutations = new Mutations();
    private final GenomGenerator genomGenerator = new GenomGenerator();

    private final  List<Integer> genome;
    private MapDirection direction;
    private Vector2d coordinate;
    private int energy;
    private int age =0;
    private int activeGene;

    // Animal constructor only for the start of the simulation
    public Animal(Vector2d coordinate) {
        this.direction = MapDirection.NORTH;
        this.coordinate = coordinate;
        this.energy = STARTING_ENERGY;
        this.age = 0;
        this.genome = genomGenerator.generateRandomGenome();
        this.activeGene = 0;
    }
    // Animal constructor for mating
    public Animal(Vector2d coordinate, int energy , List <Integer> genome ) {
        this.direction = randomDirection();
        this.coordinate = coordinate;
        this.energy = energy;
        this.genome = genome;
        this.age =0;
        this.activeGene = genomGenerator.activateRandomGene();
    }

    public Vector2d getPosition() {
        return this.coordinate;
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    public int getEnergy() {return this.energy;}

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    private void updateActiveGene(){
        this.activeGene++;
        this.activeGene %= 8;
    }

    public static MapDirection randomDirection(){
        return MapDirection.values()[(int)(Math.random() * MapDirection.values().length)];
    }

    private boolean isAt(Vector2d position) {
        return this.coordinate.equals(position);
    }

    public String toString(){
        return this.direction.toString() ;
    }

    /// poruszanie się
    /// 1. obrót aktywnygen
    /// 2. ruch
    /// 3. aktualizuj aktywny gen

    public MapDirection calculateSpinAnimal(){
        return MapDirection.values()[(this.direction.ordinal()+this.activeGene)%8];
    }

    private void spinAnimal(){
        this.direction = calculateSpinAnimal();
    }

    // calculates animal move for world map
    public Vector2d calculateMove() {
        MapDirection direction = calculateSpinAnimal();
        return this.coordinate.add(direction.toUnitVector());
    }

    // moves animal to coordinate decided in abstractWorldMap logic
    public void move(Vector2d worldPosition) {
        this.coordinate = worldPosition;
        this.energy -= ENERGY_LOSS;
        updateActiveGene();
        }
}