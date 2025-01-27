package agh.ics.oop.model.worldObjects.animal;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.enums.MapDirection;
import agh.ics.oop.model.worldObjects.WorldElement;

import java.util.List;

public class Animal implements WorldElement {
    private final int ENERGY_LOSS=2;

    private final Mutations mutations = new Mutations();
    private final GenomGenerator genomGenerator = new GenomGenerator();

    private   List<Integer> genome;
    private MapDirection direction;
    private Vector2d coordinate;
    private int energy;
    private int age =0;
    private int indexActiveGene;

    // Animal constructor only for the start of the simulation
    public Animal(Vector2d coordinate , int energy , int genomeLength) {
        this.direction = MapDirection.NORTH;
        this.coordinate = coordinate;
        this.energy = energy;
        this.age = 0;
        this.genome = genomGenerator.generateRandomGenome(genomeLength);
        this.indexActiveGene = 0;
    }
    // Animal constructor for mating
    public Animal(Vector2d coordinate, int energy , List <Integer> genome ) {
        this.direction = randomDirection();
        this.coordinate = coordinate;
        this.energy = energy;
        this.genome = genome;
        this.age =0;
        this.indexActiveGene = genomGenerator.activateRandomGene();
    }

    public void setGenome(List<Integer> genome) {
        this.genome = genome;
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
        this.indexActiveGene++;
        this.indexActiveGene %= 8;
    }

    private void ageUpAnimal(){
        this.age++;
    }

    public static MapDirection randomDirection(){
        return MapDirection.values()[(int)(Math.random() * MapDirection.values().length)];
    }

    private boolean isAt(Vector2d position) {
        return this.coordinate.equals(position);
    }

    public String toString(){
        return this.direction.toString() +
                this.genome.toString() +
                this.energy  +
                this.coordinate.toString() +
                this.indexActiveGene;
    }

    /// poruszanie się
    /// 1. obrót aktywnygen
    /// 2. ruch
    /// 3. aktualizuj aktywny gen

    public MapDirection calculateSpinAnimal(){
        return MapDirection.values()[(this.direction.ordinal()+this.genome.get(indexActiveGene))%8];
    }

    // calculates animal move for world map
    public Vector2d calculateMove() {
        MapDirection direction = calculateSpinAnimal();
        this.direction = direction;
        return this.coordinate.add(direction.toUnitVector());
    }

    // moves animal to coordinate decided in abstractWorldMap logic
    public void move(Vector2d worldPosition) {
        this.coordinate = worldPosition;
        this.energy -= ENERGY_LOSS;
        updateActiveGene();
        }
}