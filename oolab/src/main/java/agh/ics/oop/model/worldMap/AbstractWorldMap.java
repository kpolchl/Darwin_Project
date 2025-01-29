package agh.ics.oop.model.worldMap;

import agh.ics.oop.model.utils.Stats;
import agh.ics.oop.model.observators.MapChangeListener;
import agh.ics.oop.model.utils.RandomPositionGenerator;
import agh.ics.oop.model.worldObjects.Plant;
import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.WorldElement;
import agh.ics.oop.model.worldObjects.animal.Animal;
import agh.ics.oop.model.worldObjects.animal.Breeding;
import agh.ics.oop.model.worldObjects.animal.Mutations;

import java.util.*;

public abstract class AbstractWorldMap {
    protected Vector2d MinCord;
    protected Vector2d MaxCord;
    protected final Breeding breeding;
    protected final Mutations mutations = new Mutations();
    protected List<Animal> animalList = new ArrayList<Animal>();  // list of all animals on the map should be sorted by energy
    protected List<Animal> children; // temp list only for one day
    protected List<Animal> deadAnimalsList = new ArrayList<>();
    protected HashMap<Vector2d, List<Animal>> animalMap;
    protected HashMap<Vector2d, Plant> plantMap;
    protected List<MapChangeListener> observers = new ArrayList<>();

    public AbstractWorldMap(Vector2d MaxCord, int breedingPartition, int breedingEnergy) {
        this.MinCord = new Vector2d(0, 0);
        this.MaxCord = MaxCord;
        this.animalMap = new HashMap<>();
        this.breeding = new Breeding(breedingPartition, breedingEnergy);
        this.plantMap = new HashMap<>();
        this.children = new ArrayList<>();
    }

    public abstract Set<Vector2d> getPreferedPositions();

    public abstract void plantGrow(int N);

    protected abstract void eatPlant(Vector2d pos);


    public List<Animal> getAnimalList() {
        return animalList;
    }

    public List<Animal> getDeadAnimalsList() {
        return deadAnimalsList;
    }

    public List<Animal> getChildren() {
        return children;
    }

    protected List<Animal> getAllAnimals() {
        List<Animal> allAnimals = new ArrayList<>(animalList);
        allAnimals.addAll(deadAnimalsList);
        return allAnimals;
    }

    public HashMap<Vector2d, Plant> getPlantMap() {
        return plantMap;
    }

    public int getWidth() {
        return MaxCord.getX();
    }

    public int getHeight() {
        return MaxCord.getY();
    }

    public void setDeadAnimalsList(List<Animal> deadAnimalsList) {
        this.deadAnimalsList = deadAnimalsList;
    }

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

    // getters for stats
    protected int getNumberOfPlants() {
        return plantMap.size();
    }

    protected int getNumberOfAnimals() {
        return animalMap.size();
    }

    protected int getNumberOfFreeFields() {
        Set<Vector2d> usedPositions = new HashSet<>(animalMap.keySet());
        usedPositions.addAll(plantMap.keySet());
        return (MaxCord.getX() + 1) * (MaxCord.getY() + 1) - usedPositions.size();
    }

    protected List<List<Integer>> getTopThreePopularGenotypes() {
        Map<List<Integer>, Integer> genotypePopularity = new HashMap<>();

        // Zliczanie wystąpień każdego genotypu
        for (Animal animal : this.animalList) {
            genotypePopularity.merge(animal.getGenome(), 1, Integer::sum);
        }

        // Sortowanie genotypów według liczby wystąpień
        return genotypePopularity.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Sortowanie malejąco po wartości
                .limit(3) // Pobranie maksymalnie trzech najpopularniejszych
                .map(Map.Entry::getKey) // Pobranie genotypów (kluczy)
                .toList(); // Konwersja na listę
    }

    protected double getAverageAliveAnimalsEnergy() {
        List<Animal> allAnimals = this.animalList;

        return allAnimals.stream().mapToInt(Animal::getEnergy).average() // Compute the average directly
                .orElse(0.0); // Return 0.0 if the stream is empty
    }

    protected double getAverageLife() {
        if (deadAnimalsList.isEmpty()) {
            return 0.00; // Return 0.00 if the list is empty
        }
        return deadAnimalsList.stream().mapToInt(Animal::getAge) // Extract ages of dead animals
                .average() // Calculate the average
                .orElse(0.0); // Return 0.0 if the stream is empty (should not happen due to the isEmpty check)
    }

    protected double getAverageAliveAnimalsChildrenCount() {
        List<Animal> aliveAnimals = this.animalList;

        return aliveAnimals.isEmpty() ? 0.0 : (double) aliveAnimals.stream().map(Animal::getNumberOfChildren).reduce(Integer::sum).get() / aliveAnimals.size();
    }

    public void setStatistics(Stats stats, int newDay) {
        stats.setStats(this.getNumberOfAnimals(), this.getNumberOfPlants(), this.getNumberOfFreeFields(), newDay, this.getAverageAliveAnimalsEnergy(), this.getAverageLife(), this.getAverageAliveAnimalsChildrenCount(), this.getTopThreePopularGenotypes());
    }

    public void animalEat(Animal animal, int plantEnergy) {
        if (plantMap.containsKey(animal.getPosition())) {
            plantMap.remove(animal.getPosition());
            animal.eatPlant(plantEnergy); // update plant eaten and animal energy
            eatPlant(animal.getPosition()); // handles adding cord for generating plants varied by worldType
        }
    }

    public void animalBreed(Animal animal, int minimumNumOfMutations, int maximumNumOfMutations, boolean mutationType) {
        Random random = new Random();
        if (animalMap.containsKey(animal.getPosition()) && animalMap.get(animal.getPosition()).size() == 2) {

            Optional<Animal> firstAnimal = Optional.ofNullable(animalMap.get(animal.getPosition())).filter(list -> !list.isEmpty()).map(List::getFirst);
            if (firstAnimal.isPresent() && breeding.canBreed(animal) && breeding.canBreed(firstAnimal.get())) {
                Animal child = breeding.breed(firstAnimal.get(), animal);
                List<Integer> childGenome = child.getGenome();
                int numOfMutations = random.nextInt(maximumNumOfMutations) + minimumNumOfMutations;
                if (mutationType) {
                    for (int i = 0; i < numOfMutations; i++) {
                        mutations.mutateRandomGenes(childGenome);
                    }
                } else {
                    for (int i = 0; i < numOfMutations; i++) {
                        mutations.mutateRandomGenes(childGenome);
                    }
                }
                child.setGenome(childGenome);
                children.add(child);
            }

        }
    }

    public void placeAnimalonMap(Animal animal) {
        animalMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add(animal);
    }

    public boolean isOccupied(Vector2d position) {
        return animalMap.containsKey(position);
    }

    public WorldElement objectAt(Vector2d position) {
        if (animalMap.get(position) != null) return animalMap.get(position).getFirst();
        else if (plantMap.get(position) != null) return plantMap.get(position);
        return null;
    }

    protected boolean inBounds(Vector2d pos) {
        return pos.follows(MinCord) && pos.precedes(MaxCord);
    }

    // return true if animal is outside map on the pole
    protected Boolean exitMapOnThePole(Vector2d cord) {
        return cord.getY() < MinCord.getY() || cord.getY() > MaxCord.getY();
    }

    // if on the x edge of the map return true
    protected Boolean exitOnLeft(Vector2d cord) {
        return cord.getX() < MinCord.getX();
    }

    protected boolean exitOnRight(Vector2d cord) {
        return cord.getX() > MaxCord.getX();
    }

    public void setAnimalList(List<Animal> animalList) {
        this.animalList = animalList;
    }

    // handle animal move on the map
    public void moveBorderCondition(Animal animal) {

        Vector2d CordAfterMove = animal.calculateMove();

        // top bottom map condition
        if (exitMapOnThePole(CordAfterMove)) {
            animal.setDirection(animal.getDirection().opposite());
            return;
        }
        // check left right map condition
        if (exitOnLeft(CordAfterMove)) {
            CordAfterMove.setX(MaxCord.getX());
        } else if (exitOnRight(CordAfterMove)) {
            CordAfterMove.setX(MinCord.getX());
        }
        animalMap.remove(animal.getPosition());
        animal.move(CordAfterMove);
        placeAnimalonMap(animal);

    }


    // main method to handle all animal actions
    public void animalDay(int plantEnergy, int minimumNumOfMutations, int maximumNumOfMutations, int energyDepletion, boolean mutationType) {

        animalList.forEach(this::moveBorderCondition); // move all animals

        animalList.forEach(animal -> animalEat(animal, plantEnergy));

        animalList.forEach(animal -> animalBreed(animal, minimumNumOfMutations, maximumNumOfMutations, mutationType)); // breed

        animalList.forEach(Animal::ageUpAnimal);

        animalList.forEach(animal -> animal.looseEnergy(energyDepletion));

        animalList.addAll(children); // add all children to animals

        children.clear(); // clearing child list

        animalList.sort(Comparator.comparingInt(Animal::getEnergy).reversed()); // sorting all animals by energy
    }

    public void deleteDeadAnimals(int deathDay) {
        int N = animalList.size();
        for (int i = N - 1; i >= 0; i--) {
            Animal animal = animalList.get(i);
            animal.setDayOfDeath(deathDay);
            if (animal.isDead()) {
                animalMap.remove(animal.getPosition());
                animalList.remove(i);
                deadAnimalsList.add(animal);
            }
        }
    }

    public void createStartingAnimals(int numberOfAnimals, int startingEnergy, int genomeLength) {
        Set<Vector2d> usedPositions = new HashSet<>();
        Random random = new Random();

        while (usedPositions.size() < numberOfAnimals) {
            int x = random.nextInt(MaxCord.getX() + 1);
            int y = random.nextInt(MaxCord.getY() + 1);
            Vector2d position = new Vector2d(x, y);

            if (usedPositions.add(position)) { // add() zwraca false, jeśli element już istnieje
                animalList.add(new Animal(position, startingEnergy, genomeLength));
            }
        }
    }

}
