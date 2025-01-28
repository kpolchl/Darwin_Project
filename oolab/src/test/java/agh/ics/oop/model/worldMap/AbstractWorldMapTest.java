package agh.ics.oop.model.worldMap;

import agh.ics.oop.model.enums.MapDirection;
import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.Plant;
import agh.ics.oop.model.worldObjects.animal.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;

class AbstractWorldMapTest {
    private AbstractWorldMap worldMap;

    @BeforeEach
    void setUp() {
        worldMap = new AbstractWorldMap(new Vector2d(10, 10), 2, 5) {
            @Override
            public void plantGrow(int N) {
            }

            @Override
            protected void eatPlant(Vector2d pos) {
            }
        };
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
    void testPlaceAnimalonMap() {
        Animal animal = new Animal(new Vector2d(3, 3), 100, 8);
        worldMap.placeAnimalonMap(animal);

        assertTrue(worldMap.isOccupied(new Vector2d(3, 3)));
        assertEquals(animal, worldMap.objectAt(new Vector2d(3, 3)));
    }

    @Test
    void testAnimalMoveWithBorderCondition() {
        Animal animal = new Animal(new Vector2d(0, 0), 100, 8);
        animal.setDirection(MapDirection.WEST);
        worldMap.placeAnimalonMap(animal);

        worldMap.moveBorderCondition(animal);

        assertEquals(new Vector2d(10, 0), animal.getPosition());
    }

    @Test
    void testAnimalEatingPlant() {
        Plant plant = new Plant(new Vector2d(4, 4));
        Animal animal = new Animal(new Vector2d(4, 4), 100, 8);

        worldMap.getPlantMap().put(plant.getPosition(), plant);
        worldMap.placeAnimalonMap(animal);

        worldMap.animalEat(animal, 50);

        assertEquals(150, animal.getEnergy());
        assertFalse(worldMap.getPlantMap().containsKey(plant.getPosition()));
    }

    @Test
    void testAnimalBreeding() {
        Animal parent1 = new Animal(new Vector2d(5, 5), 100, 8);
        Animal parent2 = new Animal(new Vector2d(5, 5), 100, 8);
        System.out.println(parent1);
        System.out.println(parent2);
        worldMap.placeAnimalonMap(parent1);
        worldMap.placeAnimalonMap(parent2);

        worldMap.animalBreed(parent2, 1, 3, true);

        assertEquals(1, worldMap.getChildren().size());
        Animal child = worldMap.getChildren().getFirst();
        assertEquals(new Vector2d(5, 5), child.getPosition());
    }

    @Test
    void testDeleteDeadAnimals() {
        Animal aliveAnimal = new Animal(new Vector2d(5, 5), 50, 8);
        Animal deadAnimal = new Animal(new Vector2d(5, 6), 0, 8);
        List<Animal> aliveAnimalList = new ArrayList<>();
        aliveAnimalList.add(aliveAnimal);
        aliveAnimalList.add(deadAnimal);

        worldMap.setAnimalList(aliveAnimalList);

        worldMap.placeAnimalonMap(aliveAnimal);
        worldMap.placeAnimalonMap(deadAnimal);

        worldMap.deleteDeadAnimals(10);

        assertFalse(worldMap.isOccupied(new Vector2d(5, 6)));
        assertTrue(worldMap.isOccupied(new Vector2d(5, 5)));
        assertEquals(1, worldMap.getAnimalList().size());
    }
}
