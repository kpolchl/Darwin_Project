package agh.ics.oop.model.worldMap;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.Plant;

import java.util.*;

public class EquatorMap extends AbstractWorldMap {

    private static final double LOWER_BOUND_RATIO = 0.4; // 2/5
    private static final double UPPER_BOUND_RATIO = 0.6; // 3/5

    private final Set<Vector2d> preferredPositions;
    private final Set<Vector2d> nonPreferredPositions;
    private final Random rand = new Random();

    public EquatorMap(Vector2d MAX_COORD, int breedingPartition, int breedingEnergy) {
        super(MAX_COORD, breedingPartition, breedingEnergy);
        this.preferredPositions = new HashSet<>();
        this.nonPreferredPositions = new HashSet<>();

        int lowerBound = (int) (MAX_COORD.getY() * LOWER_BOUND_RATIO);
        int upperBound = (int) (MAX_COORD.getY() * UPPER_BOUND_RATIO);

        for (int i = 0; i < MAX_COORD.getY(); i++) {
            for (int j = 0; j < MAX_COORD.getX(); j++) {
                Vector2d pos = new Vector2d(j, i);
                if (i >= lowerBound && i < upperBound) {
                    preferredPositions.add(pos);
                } else {
                    nonPreferredPositions.add(pos);
                }
            }
        }
    }
    public Set<Vector2d> getPreferredPositions() {
        return preferredPositions;
    }

    public Set<Vector2d> getNonPreferredPositions() {
        return nonPreferredPositions;
    }


    @Override
    public void plantGrow(int PLANTS_PER_DAY) {
        double parettoNum = 0.8;
        int i = 0;

        // Add plants to preferred positions
        while (i < (int) (PLANTS_PER_DAY * parettoNum) && !preferredPositions.isEmpty()) {
            List<Vector2d> preferredList = new ArrayList<>(preferredPositions);
            Vector2d plantpos = preferredList.get(rand.nextInt(preferredList.size()));
            plantMap.put(plantpos, new Plant(plantpos));
            preferredPositions.remove(plantpos);
            i++;
        }

        // Add plants to non-preferred positions
        while (i < PLANTS_PER_DAY && !nonPreferredPositions.isEmpty()) {
            List<Vector2d> nonPreferredList = new ArrayList<>(nonPreferredPositions);
            Vector2d plantpos = nonPreferredList.get(rand.nextInt(nonPreferredList.size()));
            plantMap.put(plantpos, new Plant(plantpos));
            nonPreferredPositions.remove(plantpos);
            i++;
        }
    }

    protected boolean isPreferredPosition(Vector2d pos) {
        int lowerBound = (int) (MAX_COORD.getY() * LOWER_BOUND_RATIO);
        int upperBound = (int) (MAX_COORD.getY() * UPPER_BOUND_RATIO);
        return pos.getY() >= lowerBound && pos.getY() < upperBound;
    }

    @Override
    protected void eatPlant(Vector2d pos) {
        if (!preferredPositions.contains(pos) && !nonPreferredPositions.contains(pos)) {
            if (isPreferredPosition(pos)) {
                preferredPositions.add(pos);
            } else {
                nonPreferredPositions.add(pos);
            }
        }
        plantMap.remove(pos);
    }
}