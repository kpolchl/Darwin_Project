package agh.ics.oop.model.worldMap;

import agh.ics.oop.model.worldObjects.Plant;
import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.animal.Animal;
import agh.ics.oop.model.worldObjects.animal.Breeding;

import java.util.*;

import static agh.ics.oop.model.worldObjects.Plant.getPlantEnergy;

///
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
    protected List<Animal> animalList;  // list of all animals on the map should be sorted by energy
    protected List<Animal> children; // temp list only for one day
    protected HashMap<Vector2d, Plant> plantMap;
    protected final Breeding breeding = new Breeding(2,20); // temp do zmiany

    protected List<Vector2d> PREFERED_POSITIONS;
    protected List<Vector2d> LESS_PREFERED_POSITIONS;


    public AbstractWorldMap(Vector2d MAX_COORD, List<Animal> animals) {
        this.MIN_COORD = new Vector2d(0,0);
        this.MAX_COORD = MAX_COORD;
        this.animalMap = new HashMap<>();
        for (Animal animal : animals) {
            this.animalMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add(animal); // do stestowania bo nie wierzę że to działa
        }

    }

    // handle grass growth per day
    protected abstract void plantGrow(int N);

    protected abstract void eatPlant(Vector2d pos);


    protected void eatBreedPlace(Animal animal){

        // eating
        if (plantMap.containsKey(animal.getPosition())){
            animal.setEnergy(animal.getEnergy()+getPlantEnergy());
            plantMap.remove(animal.getPosition());
            eatPlant(animal.getPosition()); // handles adding cord for generating plants
        }

        //breeding
        if(animalMap.containsKey(animal.getPosition()) && animalMap.get(animal.getPosition()).size() == 1){

            Optional<Animal> firstAnimal = Optional.ofNullable(animalMap.get(animal.getPosition())) // nw też do stestowania na pewno
                    .filter(list -> !list.isEmpty())
                    .map(List::getFirst);

            // place child
            if(firstAnimal.isPresent()){
                Animal child = breeding.breed(firstAnimal.get(),animal);
                animalMap.computeIfAbsent(child.getPosition(), k -> new ArrayList<>()).add(child); // do stestowania bo nie wierzę że to działa

                children.add(child);
            }
            //place animal
            animalMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add(animal); // do stestowania bo nie wierzę że to działa
        }
        //place animal
        else{
            animalMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add(animal); // do stestowania bo nie wierzę że to działa
        }

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
        if(exitMapOnThePole(CordAfterMove)) {
            animal.setDirection(animal.getDirection().opposite());
            return;
        }
        // check left right map condition
        if(exitOnLeft(CordAfterMove)) {
            CordAfterMove.setX(MAX_COORD.getX());
        }
        else if(exitOnRight(CordAfterMove)) {
            CordAfterMove.setX(MIN_COORD.getX());
        }
        animal.move(CordAfterMove);
    }
    ///
    /// Move
    /// sort
    /// place
    /// eat
    /// breed
    protected void moveAnimalsOnMap() { // ta logika jest troche upośledzona dlaczego nie dodaje od razu przy ruchu trawy ale huj nie ruszam już przy testach się zobaczy czy działa tak jak miało

        animalList.forEach(this::move); // move all animals

        animalList.sort(Comparator.comparingInt(Animal::getEnergy).reversed()); // sort animal by energy

        animalList.forEach(this::eatBreedPlace); // place on map and eat

    }

    protected void deleteDeadAnimals() {
        int N = animalList.size();
        for (int i = N-1; i < N; i++) {
            if (animalList.get(i).getEnergy() <=0 ) {
                animalList.remove(i);
            }
            if
        }
    }

}
