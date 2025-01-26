package agh.ics.oop.model.worldObjects.animal;

import agh.ics.oop.model.utils.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest2 {

    @Test
    void randomDirection() {
    }

    @Test
    void testToString() {
    }

    @Test
    void calculateSpinAnimal() {
    }

    @Test
    void calculateMove() {
    }

    @Test
    void move() {
        Animal animal1 = new Animal(new Vector2d(1,1),100,2);
        animal1.setGenome(List.of(0, 1, 1, 1, 1, 1, 1, 1, 1, 1));
        System.out.println(animal1.toString());
        Vector2d CordAfterMove = animal1.calculateMove();
        animal1.move(CordAfterMove);
        System.out.println(CordAfterMove.toString());
        System.out.println(animal1.toString());

        Vector2d CordAfterMove1 = animal1.calculateMove();
        animal1.move(CordAfterMove1);
        System.out.println(CordAfterMove1.toString());

        System.out.println(animal1.toString());

        Vector2d CordAfterMove2 = animal1.calculateMove();
        animal1.move(CordAfterMove2);
        System.out.println(CordAfterMove2.toString());
        System.out.println(animal1.toString());
    }
}