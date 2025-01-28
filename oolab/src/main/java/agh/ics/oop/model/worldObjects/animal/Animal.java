package agh.ics.oop.model.worldObjects.animal;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.enums.MapDirection;
import agh.ics.oop.model.worldObjects.WorldElement;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement {
    private final int ENERGY_LOSS=2;

    private final Mutations mutations = new Mutations();
    private final GenomGenerator genomGenerator = new GenomGenerator();

    private List<Integer> genome;
    private MapDirection direction;
    private Vector2d coordinate;
    private int energy;
    private int age =0;
    private int indexActiveGene;
    private int dayOfDeath;
    private List<Animal> children;
    private int plantEaten=0;

    // Animal constructor only for the start of the simulation
    public Animal(Vector2d coordinate , int energy , int genomeLength) {
        this.direction = MapDirection.NORTH;
        this.coordinate = coordinate;
        this.energy = energy;
        this.age = 0;
        this.genome = genomGenerator.generateRandomGenome(genomeLength);
        this.indexActiveGene = 0;
        this.plantEaten = 0;
        this.children = new ArrayList<>();
    }
    // Animal constructor for mating
    public Animal(Vector2d coordinate, int energy , List<Integer> genome ) {
        this.direction = randomDirection();
        this.coordinate = coordinate;
        this.energy = energy;
        this.genome = genome;
        this.age =0;
        this.indexActiveGene = genomGenerator.activateRandomGene(genome.size());
        this.plantEaten = 0;
        this.children = new ArrayList<>();
    }

    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }
    public int getNumberOfChildren() {
        return this.children.size();
    }
    public int getAge(){
        return this.age;
    }

    public int getNumberOfDescendants(){
        return getDescendants().size();
    }

    public void setGenome(List<Integer> genome) {
        this.genome = genome;
    }

    public boolean isDead(){
        return this.getEnergy() <=0;
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

    public void addChild(Animal child) {
        this.children.add(child);
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    private void updateActiveGene(){
        this.indexActiveGene++;
        this.indexActiveGene %= 8;
    }
    public void eatPlant(int plantEnergy){
        this.plantEaten++;
        this.energy += plantEnergy;
    }

    public void ageUpAnimal(){
        this.age++;
    }

    public static MapDirection randomDirection(){
        return MapDirection.values()[(int)(Math.random() * MapDirection.values().length)];
    }

    private boolean isAt(Vector2d position) {
        return this.coordinate.equals(position);
    }

    public String toString(){
        return this.direction.toString();
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

    protected List<Animal> getDescendants() {
        List<Animal> descendants = new ArrayList<>(children);

        for (Animal child : children) {
            List<Animal> childDescendants = child.getDescendants();
            descendants.addAll(childDescendants);
        }
        return descendants.stream()
                .distinct()
                .toList();
    }
    public void killAnimal(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }
}