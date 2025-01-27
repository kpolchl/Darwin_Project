package agh.ics.oop.model.worldMap;
import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.Plant;
import agh.ics.oop.model.worldMap.CrawlingJungleWorld;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.*;

class CrawlingJungleWorldTest {

    @Test
    void testAddPreferredPositions() {
        CrawlingJungleWorld world = new CrawlingJungleWorld(new Vector2d(5, 5));
        Vector2d pos = new Vector2d(2, 2);

        world.plantMap.put(pos, new Plant(pos));
        world.addPreferredPositions(pos);

        assertTrue(world.PREFERED_POSITIONS.contains(new Vector2d(1, 1)));
        assertTrue(world.PREFERED_POSITIONS.contains(new Vector2d(3, 3)));
        assertFalse(world.PREFERED_POSITIONS.contains(pos));
    }

    @Test
    void testRemovePreferredPositions() {
        CrawlingJungleWorld world = new CrawlingJungleWorld(new Vector2d(5, 5));
        Vector2d pos = new Vector2d(2, 2);

        world.LESS_PREFERED_POSITIONS.add(pos);
        world.removePreferredPositions(pos);

        assertFalse(world.PREFERED_POSITIONS.contains(pos));
        assertTrue(world.LESS_PREFERED_POSITIONS.contains(pos));
    }

    @Test
    void testPlantGrowPreferredPositions() {
        CrawlingJungleWorld world = new CrawlingJungleWorld(new Vector2d(5, 5));
        world.plantGrow(3);

        assertEquals(3, world.plantMap.size());
    }

    @Test
    void testPlantGrowLessPreferredPositions() {
        CrawlingJungleWorld world = new CrawlingJungleWorld(new Vector2d(5, 5));

        // Usunięcie wszystkich preferowanych pozycji
        world.PREFERED_POSITIONS.clear();
        world.plantGrow(3);

        assertEquals(3, world.plantMap.size());
    }

    @Test
    void testEatPlant() {
        CrawlingJungleWorld world = new CrawlingJungleWorld(new Vector2d(5, 5));
        Vector2d pos = new Vector2d(2, 2);

        world.plantMap.put(pos, new Plant(pos));
        world.eatPlant(pos);

        assertTrue(world.LESS_PREFERED_POSITIONS.contains(pos));
        assertFalse(world.plantMap.containsKey(pos));
    }
}
