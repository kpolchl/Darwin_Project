package agh.ics.oop.model.worldMap;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.Plant;
import agh.ics.oop.model.worldObjects.animal.Animal;

import java.util.*;

public class EquatorMap extends AbstractWorldMap {
    private final int PLANTS_PER_DAY;

    public EquatorMap(Vector2d MAX_COORD, List<Animal> animals , int PLANTS_PER_DAY) {
        super(MAX_COORD, animals);
        this.PLANTS_PER_DAY = PLANTS_PER_DAY;
        this.PREFERED_POSITIONS = new ArrayList<>();
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
                PREFERED_POSITIONS.add(new Vector2d(j,i));
            }
        }
        for(int i =upperBound; i < MAX_COORD.getY(); i++){
            for(int j =0; j < MAX_COORD.getX(); j++){
                LESS_PREVERED_POSITIONS.add(new Vector2d(j,i));
            }
        }
    }

    @Override
    protected void grassGrow(int PLANTS_PER_DAY) {
        double parettoNum = 0.8;
        Random rand = new Random();
        // add on prevered places
        int i =0;
        while (i < (int) PLANTS_PER_DAY*parettoNum) {
            Vector2d plantpos = PREFERED_POSITIONS.get(rand.nextInt(PREFERED_POSITIONS.size()));
                plantMap.put(plantpos, new Plant(plantpos));
                PREFERED_POSITIONS.remove(plantpos);
                i++;
        }
        // add on other places
        while(i<PLANTS_PER_DAY){
            Vector2d plantpos = LESS_PREVERED_POSITIONS.get(rand.nextInt(PREFERED_POSITIONS.size()));
            plantMap.put(plantpos, new Plant(plantpos));
            LESS_PREVERED_POSITIONS.remove(plantpos);
            i++;
        }
    }

    protected boolean isPreferedPosition(Vector2d pos){
        return pos.getY() >= 2*MAX_COORD.getY()/5 && pos.getY() <= 3*MAX_COORD.getY()/5;

    }

}
