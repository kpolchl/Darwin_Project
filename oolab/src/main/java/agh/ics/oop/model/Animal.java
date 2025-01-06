package agh.ics.oop.model;

import java.util.List;

public class Animal {
    private final int GENOM_SIZE = 10;
    private final int STARTING_ENERGY = 100;
    private final GenomHandler genomHandler = new GenomHandler();
    private final  List<Integer> genome;

    private MapDirection direction;
    private Vector2d coordinate;
    private int energy;
    private int age;

    // Animal constructor only for the start of the simulation
    public Animal(Vector2d coordinate) {
        this.direction = MapDirection.NORTH;
        this.coordinate = coordinate;
        this.energy = 100;
        this.age = 0;
        this.genome = genomHandler.generateRandomGenom(GENOM_SIZE);
    }
    // Animal constructor for mating
    // dominant more energy submissive less
    public Animal(Animal dominant, Animal submissive , int energy , Vector2d coordinate) {
        this.direction = MapDirection.NORTH;
        this.coordinate = coordinate;
        this.energy = energy;
        this.genome = genomHandler.generateGenomByMating(dominant, submissive);
        this.age =0;
    }
    public Vector2d getPosition() {
        return this.coordinate;
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public int getEnergy() {return this.energy;}

    public String toString(){
        return  this.direction.toString() ;
    }

    private boolean isAt(Vector2d position) {
        return this.coordinate.equals(position);
    }

    public void move(){
        this.coordinate.add(this.direction.toUnitVector());
        }
}