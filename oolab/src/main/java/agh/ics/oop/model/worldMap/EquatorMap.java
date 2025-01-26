package agh.ics.oop.model.worldMap;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.Plant;
import agh.ics.oop.model.worldObjects.animal.Animal;

import java.util.*;

public class EquatorMap extends AbstractWorldMap {
    private final int PLANTS_PER_DAY;
    private final List<Vector2d> PREVERED_POSITIONS;
    private final List<Vector2d> LESS_PREVERED_POSITIONS;

    public EquatorMap(Vector2d MAX_COORD, List<Animal> animals , int PLANTS_PER_DAY) {
        super(MAX_COORD, animals);
        this.PLANTS_PER_DAY = PLANTS_PER_DAY;
        this.PREVERED_POSITIONS = new ArrayList<>();
        this.LESS_PREVERED_POSITIONS = new ArrayList<>();

        int lowerBound = 2*MAX_COORD.getY()/5;
        int upperBound = 3*MAX_COORD.getY()/5;
        for(int i =0; i < lowerBound; i++){
            for(int j =0; j < MAX_COORD.getX(); j++){
                LESS_PREVERED_POSITIONS.add(new Vector2d(j,i));
            }
        }
        for(int i =lowerBound; i < upperBound; i++){
            for(int j =0; j < MAX_COORD.getX(); j++){
                PREVERED_POSITIONS.add(new Vector2d(j,i));
            }
        }
        for(int i =upperBound; i < MAX_COORD.getY(); i++){
            for(int j =0; j < MAX_COORD.getX(); j++){
                LESS_PREVERED_POSITIONS.add(new Vector2d(j,i));
            }
        }
    }

    @Override // N number of grass grown
    void grassGrow(int N) {
        


    }
}
