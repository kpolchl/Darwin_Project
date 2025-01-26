package agh.ics.oop.model;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.animal.Animal;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static agh.ics.oop.model.worldObjects.animal.Animal.*;

class AnimalTest {


    @Test
    void isRandomDirectionValid() {
        Animal animal1 = new Animal(new Vector2d(1,1) , 100,2);
        System.out.println(randomDirection());
    }





}