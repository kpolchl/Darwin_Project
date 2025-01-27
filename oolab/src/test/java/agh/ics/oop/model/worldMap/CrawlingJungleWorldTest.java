package agh.ics.oop.model.worldMap;
import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.Plant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CrawlingJungleWorldTest {

    @Test
    void testAddPreferredPositions() {
        CrawlingJungleWorld world = new CrawlingJungleWorld(new Vector2d(5, 5) ,1,1);
        Vector2d pos = new Vector2d(2, 2);

        world.plantMap.put(pos, new Plant(pos));
        world.addPreferredPositions(pos);

        assertTrue(world.preferredPositions.contains(new Vector2d(1, 1)));
        assertTrue(world.preferredPositions.contains(new Vector2d(3, 3)));
        assertFalse(world.preferredPositions.contains(pos));
    }

    @Test
    void testRemovePreferredPositions() {
        CrawlingJungleWorld world = new CrawlingJungleWorld(new Vector2d(5, 5),1,1);
        Vector2d pos = new Vector2d(2, 2);

        world.nonPreferredPositions.add(pos);
        world.removePreferredPositions(pos);

        assertFalse(world.preferredPositions.contains(pos));
        assertTrue(world.nonPreferredPositions.contains(pos));
    }

    @Test
    void testPlantGrowPreferredPositions() {
        CrawlingJungleWorld world = new CrawlingJungleWorld(new Vector2d(5, 5) ,1,1);
        world.plantGrow(3);

        assertEquals(3, world.plantMap.size());
    }

    @Test
    void testPlantGrowLessPreferredPositions() {
        CrawlingJungleWorld world = new CrawlingJungleWorld(new Vector2d(5, 5) ,1,1);

        // Usunięcie wszystkich preferowanych pozycji
        world.preferredPositions.clear();
        world.plantGrow(3);

        assertEquals(3, world.plantMap.size());
    }

    @Test
    void testEatPlant() {
        CrawlingJungleWorld world = new CrawlingJungleWorld(new Vector2d(5, 5),1,1);
        Vector2d pos = new Vector2d(2, 2);

        world.plantMap.put(pos, new Plant(pos));
        world.eatPlant(pos);

        assertTrue(world.nonPreferredPositions.contains(pos));
        assertFalse(world.plantMap.containsKey(pos));
    }
}
