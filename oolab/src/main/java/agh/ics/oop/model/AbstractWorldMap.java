package agh.ics.oop.model;

import agh.ics.oop.model.exceptions.IncorrectPositionException;

import java.net.CookieHandler;
import java.util.HashMap;

public abstract class AbstractWorldMap {
    protected Vector2d MIN_COORD;
    protected Vector2d MAX_COORD;
    protected int width;
    protected  HashMap<Vector2d,Animal> AnimalMap;
    protected HashMap<Vector2d,Grass> GrassMap;

    public AbstractWorldMap(Vector2d MAX_COORD, HashMap<Vector2d,Animal> Animals) {
        this.MIN_COORD = new Vector2d(0,0);
        this.MAX_COORD = MAX_COORD;
        this.AnimalMap = Animals;
    }
    abstract void grassGrow();


    // probably will change
    protected boolean putAnimal(Vector2d pos, Animal animal) throws IncorrectPositionException {
        if(inBounds(pos)){
            AnimalMap.put(pos, animal);
            return true;
        }
        throw new IncorrectPositionException(pos);
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

        AnimalMap.remove(animal.getPosition());
        animal.move(CordAfterMove);
        AnimalMap.put(animal.getPosition(), animal);
    }



}
