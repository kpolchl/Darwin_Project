package agh.ics.oop.model.worldMap;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.Plant;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CrawlingJungleWorld extends AbstractWorldMap {

    private final Set<Vector2d> preferredPositions;
    private final Set<Vector2d> nonPreferredPositions;

    public CrawlingJungleWorld(Vector2d MAX_COORD, int breedingPartition, int breedingEnergy) {
        super(MAX_COORD, breedingPartition, breedingEnergy);
        this.preferredPositions = new HashSet<>();
        this.nonPreferredPositions = new HashSet<>();

        for (int i = 0; i < MAX_COORD.getX()+1; i++) {
            for (int j = 0; j < MAX_COORD.getY()+1; j++) {
                nonPreferredPositions.add(new Vector2d(i, j));
            }
        }
    }

    private void addPreferredPositions(Vector2d position) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                Vector2d newPosition = new Vector2d(position.getX() + dx, position.getY() + dy);
                if (isValidPosition(newPosition) && !preferredPositions.contains(newPosition)) {
                    preferredPositions.add(newPosition);
                }
            }
        }
    }

    private void removePreferredPositions(Vector2d position) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                Vector2d newPosition = new Vector2d(position.getX() + dx, position.getY() + dy);
                if (isValidPosition(newPosition)) {
                    preferredPositions.remove(newPosition);
                    nonPreferredPositions.add(newPosition);
                }
            }
        }
    }

    private boolean isValidPosition(Vector2d position) {
        return position.precedes(MAX_COORD) && position.follows(MIN_COORD) && plantMap.get(position) == null;
    }

    @Override
    public void plantGrow(int PLANTS_PER_DAY) {
        double preferredRatio = 0.8;
        int preferredPlants = (int) (PLANTS_PER_DAY * preferredRatio);
        int nonPreferredPlants = PLANTS_PER_DAY - preferredPlants;

        Random rand = new Random();

        growPlantsInPositions(preferredPlants, preferredPositions, rand);
        growPlantsInPositions(nonPreferredPlants, nonPreferredPositions, rand);
    }

    private void growPlantsInPositions(int numberOfPlants, Set<Vector2d> positions, Random rand) {
        int i = 0;
        while (i < numberOfPlants && !positions.isEmpty()) {
            Vector2d newPlantPos = getRandomPosition(positions, rand);
            positions.remove(newPlantPos);
            plantMap.put(newPlantPos, new Plant(newPlantPos));
            addPreferredPositions(newPlantPos);
            i++;
        }
    }

    private Vector2d getRandomPosition(Set<Vector2d> positions, Random rand) {
        int index = rand.nextInt(positions.size());
        return positions.stream().skip(index).findFirst().orElseThrow();
    }

    @Override
    protected void eatPlant(Vector2d pos) {
        nonPreferredPositions.add(pos);
        removePreferredPositions(pos);
        plantMap.remove(pos);
    }
}