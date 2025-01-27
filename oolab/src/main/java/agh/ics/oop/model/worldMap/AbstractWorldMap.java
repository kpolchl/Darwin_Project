package agh.ics.oop.model.worldMap;

import agh.ics.oop.Stats;
import agh.ics.oop.model.observators.MapChangeListener;
import agh.ics.oop.model.utils.RandomPositionGenerator;
import agh.ics.oop.model.worldObjects.Plant;
import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.WorldElement;
import agh.ics.oop.model.worldObjects.animal.Animal;
import agh.ics.oop.model.worldObjects.animal.Breeding;
import agh.ics.oop.model.worldObjects.animal.Mutations;

import java.util.*;

/// breeding
/// 1. dodaje z listy animali do hashmapy
/// 2 . jako że posortowane po energii to przy wkładaniu
/// 3 rozmnarzaj po dwa
/// 4 dodaje na pole dziecko
/// 5 wchodzi więcej i huj nic tej logiki nie musimy mieć

public abstract class AbstractWorldMap {
    protected Vector2d MIN_COORD;
    protected Vector2d MAX_COORD;
    protected HashMap<Vector2d, List<Animal>> animalMap; // to track moves and breeding
    protected List<Animal> animalList = new ArrayList<Animal>();  // list of all animals on the map should be sorted by energy
    protected List<Animal> children; // temp list only for one day
    protected ArrayList<Animal> deadAnimalsList;
    protected HashMap<Vector2d, Plant> plantMap;
    protected final Breeding breeding;
    protected List<MapChangeListener> observers = new ArrayList<>();
    protected Mutations mutations = new Mutations();

    protected List<Vector2d> preferredPositions;
    protected List<Vector2d> nonPreferredPositions;


    public AbstractWorldMap(Vector2d MAX_COORD, int breedingPartition, int breedingEnergy) {
        this.MIN_COORD = new Vector2d(0, 0);
        this.MAX_COORD = MAX_COORD;
        this.animalMap = new HashMap<>();
        this.breeding = new Breeding(breedingPartition, breedingEnergy);
        this.plantMap = new HashMap<>();
    }


    public HashMap<Vector2d, Plant> getPlantMap() {
        return plantMap;
    }

    public abstract void plantGrow(int N);

    protected abstract void eatPlant(Vector2d pos);

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    public void mapChanged(Stats statistics) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, statistics);
        }
    }
    protected List<Animal> getAllAnimals() {
        List<Animal> allAnimals = new ArrayList<>(animalList);
        allAnimals.addAll(deadAnimalsList);
        return allAnimals;
    }

    protected int getNumberOfPlants() {
        return plantMap.size();
    }

    protected int getNumberOfAnimals() {
        return animalMap.size();
    }

    protected int getNumberOfFreeFields() {
        Set<Vector2d> usedPositions = new HashSet<>(animalMap.keySet());
        usedPositions.addAll(plantMap.keySet());
        return MAX_COORD.getX() * MAX_COORD.getY() - usedPositions.size();
    }

    protected List<Integer> getMostPopularGenotype() {
        Map<List<Integer>, Integer> genotypePopularity = new HashMap<>();

        // Populate the genotype popularity map
        for (Animal animal : this.animalList) {
            genotypePopularity.merge(animal.getGenome(), 1, Integer::sum);
        }

        // Find the single most popular genotype
        return genotypePopularity.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()) // Find the entry with the highest value
                .map(Map.Entry::getKey)           // Extract the genotype (key)
                .orElse(Collections.emptyList()); // Return an empty list if no genotype exists
    }


    protected double getAverageAliveAnimalsEnergy() {
        List<Animal> allAnimals = this.getAllAnimals();

        return allAnimals.stream()
                .mapToInt(Animal::getEnergy)
                .average() // Compute the average directly
                .orElse(0.0); // Return 0.0 if the stream is empty
    }

    protected double getAverageLife() {
        return deadAnimalsList.isEmpty() ? 0.00 : deadAnimalsList.stream().mapToInt(Animal::getEnergy).average().orElse(0.0) / deadAnimalsList.size();
    }

    protected double getAverageAliveAnimalsChildrenCount() {
        List<Animal> allAnimals = this.getAllAnimals();

        return allAnimals.isEmpty() ? 0.0 : (double) allAnimals.stream()
                .map(Animal::getNumberOfChildren)
                .reduce(Integer::sum)
                .get() / allAnimals.size();
    }

    public void setStatistics(Stats stats, int newDay) {
        stats.setStats(this.getNumberOfAnimals(),
                this.getNumberOfPlants(),
                this.getNumberOfFreeFields(),
                newDay,
                this.getAverageAliveAnimalsEnergy(),
                this.getAverageLife(),
                this.getAverageAliveAnimalsChildrenCount(),
                this.getMostPopularGenotype());
    }

    public void animalEat(Animal animal, int plantEnergy) {
        if (plantMap.containsKey(animal.getPosition())) {
            animal.setEnergy(animal.getEnergy() + plantEnergy);
            plantMap.remove(animal.getPosition());
            eatPlant(animal.getPosition()); // handles adding cord for generating plants
        }
    }

    public void animalBreed(Animal animal, int minimumNumOfMutations, int maximumNumOfMutations, boolean mutationType) {
        Random random = new Random();
        if (animalMap.containsKey(animal.getPosition()) && animalMap.get(animal.getPosition()).size() == 1) {

            Optional<Animal> firstAnimal = Optional.ofNullable(animalMap.get(animal.getPosition())) // nw też do stestowania na pewno
                    .filter(list -> !list.isEmpty())
                    .map(List::getFirst);
            if (firstAnimal.isPresent()) {
                Animal child = breeding.breed(firstAnimal.get(), animal);
                if (mutationType) {
                    for (int i = 0; i < random.nextInt(maximumNumOfMutations) + minimumNumOfMutations; i++) {
                        mutations.mutateRandomGenes(child.getGenome());
                    }
                } else {
                    for (int i = 0; i < random.nextInt(maximumNumOfMutations) + minimumNumOfMutations; i++) {
                        mutations.mutateRandomGenes(child.getGenome());
                    }
                }
                children.add(child);
            }

        }
    }


    protected void placeAnimal(Animal animal) {
        animalMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add(animal); // do stestowania bo nie wierzę że to działa
    }


    public boolean isOccupied(Vector2d position) {
        return animalMap.containsKey(position);
    }

    public WorldElement objectAt(Vector2d position) {
        if (animalMap.get(position) != null) return animalMap.get(position).getFirst();
        return null;
    }

    protected boolean inBounds(Vector2d pos) {
        return pos.follows(MIN_COORD) && pos.precedes(MAX_COORD);
    }

    // return true if animal is outside map on the pole
    protected Boolean exitMapOnThePole(Vector2d cord) {
        return cord.getY() < MIN_COORD.getY() || cord.getY() > MAX_COORD.getY();
    }

    // if on the x edge of the map return true
    protected Boolean exitOnLeft(Vector2d cord) {
        return cord.getX() < MIN_COORD.getX();
    }

    protected boolean exitOnRight(Vector2d cord) {
        return cord.getX() > MAX_COORD.getX();
    }

    // handle animal move on the map
    protected void move(Animal animal) {

        Vector2d CordAfterMove = animal.calculateMove();

        // top bottom map condition
        if (exitMapOnThePole(CordAfterMove)) {
            animal.setDirection(animal.getDirection().opposite());
            return;
        }
        // check left right map condition
        if (exitOnLeft(CordAfterMove)) {
            CordAfterMove.setX(MAX_COORD.getX());
        } else if (exitOnRight(CordAfterMove)) {
            CordAfterMove.setX(MIN_COORD.getX());
        }
        animal.move(CordAfterMove);
    }

    /// Move
    /// sort
    /// place
    /// eat
    /// breed
    public void animalDay(int plantEnergy, int minimumNumOfMutations, int maximumNumOfMutations, boolean mutationType) { // ta logika jest troche upośledzona dlaczego nie dodaje od razu przy ruchu trawy ale huj nie ruszam już przy testach się zobaczy czy działa tak jak miało

        animalList.forEach(this::move); // move all animals

        animalList.forEach(animal -> animalEat(animal, plantEnergy));

        animalList.forEach(animal -> animalBreed(animal, minimumNumOfMutations, maximumNumOfMutations, mutationType)); // breed

        animalList.addAll(children); // add all children to animals

        animalList.forEach(this::placeAnimal);

        children.clear(); // clearing child list

        animalList.sort(Comparator.comparingInt(Animal::getEnergy).reversed()); // sorting all animals by energy
    }

    public void deleteDeadAnimals() {
        int N = animalList.size();
        for (int i = N - 1; i < N; i++) {
            if (animalList.get(i).isDead()) {
                animalList.remove(i);
                deadAnimalsList.add(animalList.get(i));
            } else {
                return;
            }
        }
    }

    public void createStartingAnimals(int numberOfAnimals, int startingEnergy, int genomeLength) {
        RandomPositionGenerator randGenerator = new RandomPositionGenerator(MAX_COORD.getX(), MAX_COORD.getY(), numberOfAnimals);
        for (Vector2d position : randGenerator) {
            animalList.add(new Animal(position, startingEnergy, genomeLength));
        }
    }

}
