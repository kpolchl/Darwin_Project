package agh.ics.oop.model.worldMap;

import agh.ics.oop.World;
import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.Plant;
import agh.ics.oop.model.worldObjects.animal.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrawlingJungleWorld extends AbstractWorldMap {

    public CrawlingJungleWorld(Vector2d MAX_COORD , int breedingPartition , int breedingEnergy) {
        super(MAX_COORD , breedingPartition , breedingEnergy);
        this.PREFERED_POSITIONS = new ArrayList<>();
        this.LESS_PREFERED_POSITIONS = new ArrayList<>();

        for(int i = 0; i < MAX_COORD.getX(); i++) {
            for(int j = 0; j < MAX_COORD.getY(); j++) {
                LESS_PREFERED_POSITIONS.add(new Vector2d(i, j));
            }
        }


    }

    // zwróć na to uwage
    private void addPreferredPositions(Vector2d position) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                Vector2d newPosition = new Vector2d(position.getX() + dx, position.getY() + dy);
                if (plantMap.get(newPosition) == null && newPosition.precedes(MAX_COORD) && newPosition.follows(MIN_COORD) && !PREFERED_POSITIONS.contains(newPosition)) {
                    PREFERED_POSITIONS.add(newPosition);
                }
            }
        }
    }

    // oraz na to
    private void removePreferredPositions(Vector2d position) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                Vector2d newPosition = new Vector2d(position.getX() + dx, position.getY() + dy);

                if (newPosition.precedes(MIN_COORD) && newPosition.follows(MIN_COORD)) {
                    PREFERED_POSITIONS.remove(newPosition);
                    if (!LESS_PREFERED_POSITIONS.contains(newPosition)) { // if not necesssary should be checked better save than sorry
                        LESS_PREFERED_POSITIONS.add(newPosition);
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
        while (i < (int) PLANTS_PER_DAY*parrettNum && !LESS_PREFERED_POSITIONS.isEmpty() ) {
            if(PREFERED_POSITIONS.isEmpty()) {
                Vector2d newPlantPos = LESS_PREFERED_POSITIONS.get(rand.nextInt(LESS_PREFERED_POSITIONS.size()));
                plantMap.put(newPlantPos, new Plant(newPlantPos));
                LESS_PREFERED_POSITIONS.remove(newPlantPos);
                addPreferredPositions(newPlantPos);
            }
            else{
                Vector2d newPlantPos = PREFERED_POSITIONS.get(rand.nextInt(PREFERED_POSITIONS.size()));
                PREFERED_POSITIONS.remove(newPlantPos);
                plantMap.put(newPlantPos, new Plant(newPlantPos));
                addPreferredPositions(newPlantPos);
            }
            i++;
        }
        while( i <PLANTS_PER_DAY && !LESS_PREFERED_POSITIONS.isEmpty()) {
            Vector2d newPlantPos = PREFERED_POSITIONS.get(rand.nextInt(PREFERED_POSITIONS.size()));
            PREFERED_POSITIONS.remove(newPlantPos);
            plantMap.put(newPlantPos, new Plant(newPlantPos));
            addPreferredPositions(newPlantPos);
            i++;
        }
    }

    @Override
    protected void eatPlant(Vector2d pos) {
        LESS_PREFERED_POSITIONS.add(pos);
        removePreferredPositions(pos);
    }
}
