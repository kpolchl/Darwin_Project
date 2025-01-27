package agh.ics.oop.model.worldMap;

import agh.ics.oop.Stats;
import agh.ics.oop.model.enums.MapDirection;
import agh.ics.oop.model.observators.MapChangeListener;
import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.Plant;
import agh.ics.oop.model.worldObjects.animal.Animal;
import agh.ics.oop.model.worldMap.AbstractWorldMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;

class AbstractWorldMapTest {
    private AbstractWorldMap worldMap;

    @BeforeEach
    void setUp() {
        worldMap = new TestWorldMap(new Vector2d(10, 10), 2, 5);
    }

    @Test
    void testCreateStartingAnimals() {
        worldMap.createStartingAnimals(5, 100, 8);

        assertEquals(5, worldMap.getAnimalList().size());
        worldMap.getAnimalList().forEach(animal -> {
            assertEquals(100, animal.getEnergy());
            assertEquals(8, animal.getGenome().size());
        });
    }

    @Test
    void testPlaceAnimal() {
        Animal animal = new Animal(new Vector2d(3, 3), 100, 8);
        worldMap.placeAnimal(animal);

        assertTrue(worldMap.isOccupied(new Vector2d(3, 3)));
        assertEquals(animal, worldMap.objectAt(new Vector2d(3, 3)));
    }

    @Test
    void testAnimalMoveWithBorderCondition() {
        Animal animal = new Animal(new Vector2d(0, 0), 100, 8);
        animal.setDirection(MapDirection.WEST);
        worldMap.placeAnimal(animal);

        worldMap.moveBorderCondition(animal);

        assertEquals(new Vector2d(10, 0), animal.getPosition());
    }

    @Test
    void testAnimalEatingPlant() {
        Plant plant = new Plant(new Vector2d(4, 4));
        Animal animal = new Animal(new Vector2d(4, 4), 100, 8);

        worldMap.getPlantMap().put(plant.getPosition(), plant);
        worldMap.placeAnimal(animal);

        worldMap.animalEat(animal, 50);

        assertEquals(150, animal.getEnergy());
        assertFalse(worldMap.getPlantMap().containsKey(plant.getPosition()));
    }

    @Test
    void testAnimalBreeding() {
        Animal parent1 = new Animal(new Vector2d(5, 5), 100, 8);
        Animal parent2 = new Animal(new Vector2d(5, 5), 100, 8);
        worldMap.placeAnimal(parent1);
        worldMap.placeAnimal(parent2);

        worldMap.animalBreed(parent1, 1, 3, true);

        assertEquals(1, worldMap.getChildren().size());
        Animal child = worldMap.getChildren().getFirst();
        assertEquals(new Vector2d(5, 5), child.getPosition());
    }

    @Test
    void testDeleteDeadAnimals() {
        Animal aliveAnimal = new Animal(new Vector2d(5, 5), 50, 8);
        Animal deadAnimal = new Animal(new Vector2d(5, 6), 0, 8);
        worldMap.placeAnimal(aliveAnimal);
        worldMap.placeAnimal(deadAnimal);

        worldMap.deleteDeadAnimals();

        assertFalse(worldMap.isOccupied(new Vector2d(5, 6)));
        assertTrue(worldMap.isOccupied(new Vector2d(5, 5)));
        assertEquals(1, worldMap.getAnimalList().size());
    }

//    @Test
//    void testSetStatistics() {
//        Stats stats = mock(Stats.class);
//        worldMap.createStartingAnimals(5, 100, 8);
//        worldMap.getPlantMap().put(new Vector2d(3, 3), new Plant(new Vector2d(3, 3)));
//
//        worldMap.setStatistics(stats, 1);
//
//        verify(stats).setStats(
//                eq(5),
//                eq(1),
//                eq(99),
//                eq(1),
//                anyDouble(),
//                anyDouble(),
//                anyDouble(),
//                anyList()
//        );
//    }

    @Test
    void testAnimalDayWorkflow() {
        worldMap.createStartingAnimals(5, 100, 8);

        worldMap.animalDay(50, 1, 3, true);

        assertTrue(worldMap.getAnimalList().size() >= 5);
    }

    // Klasa testowa, aby zaimplementować brakujące metody
    private static class TestWorldMap extends AbstractWorldMap {

        public TestWorldMap(Vector2d MAX_COORD, int breedingPartition, int breedingEnergy) {
            super(MAX_COORD, breedingPartition, breedingEnergy);
        }

        @Override
        public void plantGrow(int N) {
            for (int i = 0; i < N; i++) {
                Vector2d position = new Vector2d(i % MAX_COORD.getX(), i / MAX_COORD.getY());
                plantMap.put(position, new Plant(position));
            }
        }

        @Override
        protected void eatPlant(Vector2d pos) {
            plantMap.remove(pos);
        }
    }
}
