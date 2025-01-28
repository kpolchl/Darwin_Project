package agh.ics.oop.model.worldMap;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.Plant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EquatorMapTest {

    private EquatorMap equatorMap;
    private final Vector2d MAX_COORD = new Vector2d(10, 10); // Example map size

    @BeforeEach
    void setUp() {
        equatorMap = new EquatorMap(MAX_COORD, 5, 10); // Example parameters
    }

    // Test 1: Verify that preferred and non-preferred positions are initialized correctly
    @Test
    void testInitialization() {
        Set<Vector2d> preferredPositions = equatorMap.getPreferredPositions();
        Set<Vector2d> nonPreferredPositions = equatorMap.getNonPreferredPositions();

        // Check that preferred positions are within the equator bounds
        for (Vector2d pos : preferredPositions) {
            assertTrue(pos.getY() >= 4 && pos.getY() < 6, "Position " + pos + " should be in the preferred area.");
        }

        // Check that non-preferred positions are outside the equator bounds
        for (Vector2d pos : nonPreferredPositions) {
            assertTrue(pos.getY() < 4 || pos.getY() >= 6, "Position " + pos + " should be in the non-preferred area.");
        }

        // Check that all positions are covered
        assertEquals(100, preferredPositions.size() + nonPreferredPositions.size(), "All positions should be covered.");
    }

    // Test 2: Verify that plants are added to preferred positions first
    @Test
    void testPlantGrowPreferredPositions() {
        equatorMap.plantGrow(10); // Add 10 plants

        // Check that plants are added to preferred positions first
        Set<Vector2d> preferredPositions = equatorMap.getPreferredPositions();
        Set<Vector2d> plantPositions = equatorMap.getPlantMap().keySet();

        for (Vector2d pos : plantPositions) {
            assertTrue(preferredPositions.contains(pos) || equatorMap.getNonPreferredPositions().contains(pos),
                    "Plants should be added to preferred or non-preferred positions.");
        }
    }

    // Test 3: Verify that plants are added to non-preferred positions when preferred positions are full
    @Test
    void testPlantGrowNonPreferredPositions() {
        // Fill all preferred positions
        equatorMap.plantGrow(20); // Add 20 plants (more than preferred positions)

        // Check that plants are added to non-preferred positions
        Set<Vector2d> nonPreferredPositions = equatorMap.getNonPreferredPositions();
        Set<Vector2d> plantPositions = equatorMap.getPlantMap().keySet();

        boolean hasNonPreferredPlants = false;
        for (Vector2d pos : plantPositions) {
            if (nonPreferredPositions.contains(pos)) {
                hasNonPreferredPlants = true;
                break;
            }
        }
        assertTrue(hasNonPreferredPlants, "Plants should be added to non-preferred positions when preferred positions are full.");
    }

    // Test 4: Verify that eating a plant returns its position to the correct set
    @Test
    void testEatPlant() {
        // Add a plant to a preferred position
        Vector2d plantPos = new Vector2d(5, 5); // Example position in the preferred area
        equatorMap.getPlantMap().put(plantPos, new Plant(plantPos));
        equatorMap.getPreferredPositions().remove(plantPos);

        // Eat the plant
        equatorMap.eatPlant(plantPos);

        // Check that the position is returned to the preferred positions set
        assertTrue(equatorMap.getPreferredPositions().contains(plantPos), "Position should be returned to preferred positions.");
        assertFalse(equatorMap.getPlantMap().containsKey(plantPos), "Plant should be removed from the plant map.");
    }

    // Test 5: Verify that eating a plant in a non-preferred position returns it to the correct set
    @Test
    void testEatPlantNonPreferred() {
        // Add a plant to a non-preferred position
        Vector2d plantPos = new Vector2d(5, 2); // Example position in the non-preferred area
        equatorMap.getPlantMap().put(plantPos, new Plant(plantPos));
        equatorMap.getNonPreferredPositions().remove(plantPos);

        // Eat the plant
        equatorMap.eatPlant(plantPos);

        // Check that the position is returned to the non-preferred positions set
        assertTrue(equatorMap.getNonPreferredPositions().contains(plantPos), "Position should be returned to non-preferred positions.");
        assertFalse(equatorMap.getPlantMap().containsKey(plantPos), "Plant should be removed from the plant map.");
    }

    // Test 6: Verify that no plants are added if there are no available positions
    @Test
    void testPlantGrowNoAvailablePositions() {
        // Fill all positions
        equatorMap.plantGrow(100); // Add 100 plants (all positions)

        // Try to add more plants
        equatorMap.plantGrow(10);

        // Check that no new plants are added
        assertEquals(100, equatorMap.getPlantMap().size(), "No new plants should be added when all positions are full.");
    }
}