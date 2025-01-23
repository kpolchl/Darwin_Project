package agh.ics.oop.model;

import agh.ics.oop.model.enums.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Animal {
    private final int GENOM_SIZE = 10;
    private final int STARTING_ENERGY = 100;
    private final int ENERGY_PARTITION =3;
    private final int BREEDING_ENERGY = 30;
    private final int ENERGY_LOSS=2;

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
        this.genome = generateRandomGenome(GENOM_SIZE);
        this.activeGene = 0;
    }
    // Animal constructor for mating
    public Animal(Vector2d coordinate, int energy , List <Integer> genome ) {
        this.direction = randomDirection();
        this.coordinate = coordinate;
        this.energy = energy;
        this.genome = genome;
        this.age =0;
        this.activeGene = activateRandomGene();
    }

    public Vector2d getPosition() {
        return this.coordinate;
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public int getEnergy() {return this.energy;}



    public static List<Integer>  generateRandomGenome(int size) {
        List<Integer> genom = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            genom.add((int)(Math.random() * 8));
        }
        return genom;
    }

    private void updateActiveGene(){
        this.activeGene++;
        this.activeGene %= 8;
    }

    public static List<Integer> generateGenomeByMating(List<Integer> dominantGenome, List<Integer> submissiveGenome , int energy1 ,int energy2) {

        int genomSize = submissiveGenome.size();
        if(energy2 > energy1) {
            List<Integer> temp = submissiveGenome;
            dominantGenome = submissiveGenome;
            submissiveGenome = temp;
        }

        int leftSplitIndex = (int) (((Math.max(energy1, energy2))/ (double) (energy1+energy2)) * genomSize);
        int rightSplitIndex = (int) (( Math.min(energy1,energy2)/ (double) (energy1+energy2)) * genomSize);

        boolean dominantOnLeft = Math.random() < 0.5;
        if (dominantOnLeft) {
            List<Integer> dominantGenomSublist = dominantGenome.subList(0, leftSplitIndex);
            List<Integer> submissiveGenomSublist = submissiveGenome.subList(leftSplitIndex, genomSize);
            return Stream.concat(dominantGenomSublist.stream(), submissiveGenomSublist.stream()).toList(); // concatenation using stream
        }
        else {
            List<Integer> dominantGenomSublist = dominantGenome.subList(rightSplitIndex, genomSize );
            List<Integer> submissiveGenomSublist = submissiveGenome.subList(0, rightSplitIndex);
            return Stream.concat(submissiveGenomSublist.stream(), dominantGenomSublist.stream()).toList();
        }
    }

    private boolean canBreed() {
        return this.energy>BREEDING_ENERGY;
    }
    public static void switchRandomGenes(List<Integer> genome) {
        int gene1Index = (int)(Math.random() * genome.size());
        int gene2Index = (int)(Math.random() * genome.size());
        System.out.println(gene1Index);
        System.out.println(gene2Index);
        int temp = genome.get(gene1Index);
        genome.set(gene1Index, genome.get(gene2Index));
        genome.set(gene2Index, temp);
    }

    public static MapDirection randomDirection(){
        return MapDirection.values()[(int)(Math.random() * MapDirection.values().length)];
    }

    private int activateRandomGene() {
        return (int)(Math.random()*GENOM_SIZE);
    }

    private int calculateEnergyBreedingLoss(){
        return  (this.getEnergy()-1)/ENERGY_PARTITION+1; // custom ceil function found on stack overflow
    }

    public void breed(Animal other){
        if (this.canBreed() && other.canBreed() ) {
            int newEnergy = this.calculateEnergyBreedingLoss() + other.calculateEnergyBreedingLoss();
            this.energy -= this.calculateEnergyBreedingLoss();
            other.energy -= this.calculateEnergyBreedingLoss();

            List<Integer> newGenome = generateGenomeByMating(this.getGenome() ,other.getGenome() ,this.getEnergy() ,other.getEnergy());
            Animal child = new Animal(this.getPosition(), newEnergy, newGenome);
        }
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