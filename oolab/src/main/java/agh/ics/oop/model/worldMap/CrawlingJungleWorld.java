package agh.ics.oop.model.worldMap;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.Plant;

import java.util.ArrayList;
import java.util.Random;

public class CrawlingJungleWorld extends AbstractWorldMap {

    public CrawlingJungleWorld(Vector2d MAX_COORD , int breedingPartition , int breedingEnergy) {
        super(MAX_COORD , breedingPartition , breedingEnergy);
        this.preferredPositions = new ArrayList<>();
        this.nonPreferredPositions = new ArrayList<>();

        for(int i = 0; i < MAX_COORD.getX(); i++) {
            for(int j = 0; j < MAX_COORD.getY(); j++) {
                nonPreferredPositions.add(new Vector2d(i, j));
            }
        }


    }

    // zwróć na to uwage
    void addPreferredPositions(Vector2d position) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                Vector2d newPosition = new Vector2d(position.getX() + dx, position.getY() + dy);
                if (plantMap.get(newPosition) == null && newPosition.precedes(MAX_COORD) && newPosition.follows(MIN_COORD) && !preferredPositions.contains(newPosition)) {
                    preferredPositions.add(newPosition);
                }
            }
        }
    }

    // oraz na to
    void removePreferredPositions(Vector2d position) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                Vector2d newPosition = new Vector2d(position.getX() + dx, position.getY() + dy);

                if (newPosition.precedes(MIN_COORD) && newPosition.follows(MIN_COORD)) {
                    preferredPositions.remove(newPosition);
                    if (!nonPreferredPositions.contains(newPosition)) { // if not necesssary should be checked better save than sorry
                        nonPreferredPositions.add(newPosition);
                    }

                }
            }
        }
    }

    @Override
    public void plantGrow(int PLANTS_PER_DAY) {
        double parrettNum =0.8;
        int i =0;
        Random rand = new Random();
        while (i < (int) PLANTS_PER_DAY*parrettNum && !nonPreferredPositions.isEmpty() ) {
            if(preferredPositions.isEmpty()) {
                Vector2d newPlantPos = nonPreferredPositions.get(rand.nextInt(nonPreferredPositions.size()));
                plantMap.put(newPlantPos, new Plant(newPlantPos));
                nonPreferredPositions.remove(newPlantPos);
                addPreferredPositions(newPlantPos);
            }
            else{
                Vector2d newPlantPos = preferredPositions.get(rand.nextInt(preferredPositions.size()));
                preferredPositions.remove(newPlantPos);
                plantMap.put(newPlantPos, new Plant(newPlantPos));
                addPreferredPositions(newPlantPos);
            }
            i++;
        }
        while( i <PLANTS_PER_DAY && !nonPreferredPositions.isEmpty()) {
            Vector2d newPlantPos = preferredPositions.get(rand.nextInt(preferredPositions.size()));
            preferredPositions.remove(newPlantPos);
            plantMap.put(newPlantPos, new Plant(newPlantPos));
            addPreferredPositions(newPlantPos);
            i++;
        }
    }

    @Override
    protected void eatPlant(Vector2d pos) {
        nonPreferredPositions.add(pos);
        removePreferredPositions(pos);
        plantMap.remove(pos);
    }
}
