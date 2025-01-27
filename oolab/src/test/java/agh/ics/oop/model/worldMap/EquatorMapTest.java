package agh.ics.oop.model.worldMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldMap.EquatorMap;
import agh.ics.oop.model.worldObjects.Plant;

import java.util.HashMap;
import java.util.List;

class EquatorMapTest {

    private EquatorMap map;
    private final Vector2d maxCoord = new Vector2d(10, 10);

    @BeforeEach
    void setUp() {
        map = new EquatorMap(maxCoord,1,1);
    }

    @Test
    void testMapInitialization() {
        // Ensure prefered and less prefered positions are initialized correctly
        int lowerBound = 2 * maxCoord.getY() / 5;
        int upperBound = 3 * maxCoord.getY() / 5;

        for (int y = 0; y < lowerBound; y++) {
            for (int x = 0; x < maxCoord.getX(); x++) {
                assertTrue(map.LESS_PREFERED_POSITIONS.contains(new Vector2d(x, y)));
            }
        }
        for (int y = lowerBound; y < upperBound; y++) {
            for (int x = 0; x < maxCoord.getX(); x++) {
                assertTrue(map.PREFERED_POSITIONS.contains(new Vector2d(x, y)));
            }
        }
        for (int y = upperBound; y < maxCoord.getY(); y++) {
            for (int x = 0; x < maxCoord.getX(); x++) {
                assertTrue(map.LESS_PREFERED_POSITIONS.contains(new Vector2d(x, y)));
            }
        }
    }

    @Test
    void testPlantGrow() {
        int plantsPerDay = 5;
        map.plantGrow(plantsPerDay);

        // Ensure plants are added to the map
        HashMap<Vector2d, Plant> plantMap = map.getPlantMap();
        assertEquals(plantsPerDay, plantMap.size());

        // Check if some plants are in prefered positions
        long preferedPlants = plantMap.keySet().stream().filter(map::isPreferedPosition).count();
        assertTrue(preferedPlants > 0);

        // Ensure remaining positions are updated
        assertEquals(10 * 10 - plantsPerDay, map.PREFERED_POSITIONS.size() + map.LESS_PREFERED_POSITIONS.size());
    }

    @Test
    void testEatPlant() {
        int plantsPerDay = 5;
        map.plantGrow(plantsPerDay);

        Vector2d pos = map.getPlantMap().keySet().iterator().next();
        boolean wasPreferred = map.isPreferedPosition(pos);

        // Simulate eating a plant
        map.eatPlant(pos);
        assertNull(map.getPlantMap().get(pos));

        if (wasPreferred) {
            assertTrue(map.PREFERED_POSITIONS.contains(pos));
        } else {
            assertTrue(map.LESS_PREFERED_POSITIONS.contains(pos));
        }
    }

    @Test
    void testPlantGrowNoAvailablePositions() {
        // Simulate a full map by removing all free positions
        map.PREFERED_POSITIONS.clear();
        map.LESS_PREFERED_POSITIONS.clear();

        int plantsPerDay = 5;
        map.plantGrow(plantsPerDay);

        // Ensure no plants are added when no positions are available
        assertTrue(map.getPlantMap().isEmpty());
    }
}
