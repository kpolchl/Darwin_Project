package agh.ics.oop.model.worldMap;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.Plant;

import java.util.*;

public class EquatorMap extends AbstractWorldMap {

    public EquatorMap(Vector2d MAX_COORD , int breedingPartition , int breedingEnergy) {
        super(MAX_COORD , breedingPartition , breedingEnergy);
        this.preferredPositions = new ArrayList<>();
        this.nonPreferredPositions = new ArrayList<>();

        int lowerBound = 2*MAX_COORD.getY()/5;
        int upperBound = 3*MAX_COORD.getY()/5;
        for(int i =0; i < lowerBound; i++){
            for(int j =0; j < MAX_COORD.getX(); j++){
                nonPreferredPositions.add(new Vector2d(j,i));
            }
        }
        for(int i =lowerBound; i < upperBound; i++){
            for(int j =0; j < MAX_COORD.getX(); j++){
                preferredPositions.add(new Vector2d(j,i));
            }
        }
        for(int i =upperBound; i < MAX_COORD.getY(); i++){
            for(int j =0; j < MAX_COORD.getX(); j++){
                nonPreferredPositions.add(new Vector2d(j,i));
            }
        }
    }

    @Override
    public void plantGrow(int PLANTS_PER_DAY) {
        double parettoNum = 0.8;
        Random rand = new Random();
        // add on prevered places
        int i =0;
        while (i < (int) PLANTS_PER_DAY*parettoNum && !preferredPositions.isEmpty()) {
            Vector2d plantpos = preferredPositions.get(rand.nextInt(preferredPositions.size()));
                plantMap.put(plantpos, new Plant(plantpos));
                preferredPositions.remove(plantpos);
                i++;
        }
        // add on other places
        while(i<PLANTS_PER_DAY && !nonPreferredPositions.isEmpty()) {
            Vector2d plantpos = nonPreferredPositions.get(rand.nextInt(preferredPositions.size()));
            plantMap.put(plantpos, new Plant(plantpos));
            nonPreferredPositions.remove(plantpos);
            i++;
        }
    }

    protected boolean isPreferedPosition(Vector2d pos){
        return pos.getY() >= 2*MAX_COORD.getY()/5 && pos.getY() <= 3*MAX_COORD.getY()/5;

    }

    @Override
    protected void eatPlant(Vector2d pos) {
        if( isPreferedPosition(pos)){
            preferredPositions.add(pos);
        }
        else{
            nonPreferredPositions.add(pos);
        }
        plantMap.remove(pos);
    }

}
