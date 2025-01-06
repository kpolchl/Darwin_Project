package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenomHandlerTest {

    @Test
    void generateRandomGenome() {
        GenomHandler genomHandler = new GenomHandler();
        List<Integer> randomGenom = genomHandler.generateRandomGenom(10);
        System.out.println(randomGenom);
    }
    // hmm it's random so difficult to test but it works
    @Test
    void isMatingGenomeValid() throws NoSuchFieldException, IllegalAccessException {
        GenomHandler genomHandler = new GenomHandler();
        Animal animal1 = new Animal(new Vector2d(1,1));
        Animal animal2 = new Animal(new Vector2d(2,2));
        Field edit1 = animal1.getClass().getDeclaredField("genome");
        Field edit2 = animal2.getClass().getDeclaredField("genome");
        Field editEnergy1 = animal1.getClass().getDeclaredField("energy");
        edit1.setAccessible(true);
        edit2.setAccessible(true);
        editEnergy1.setAccessible(true);
        editEnergy1.set(animal1, 200);
        edit1.set(animal1 ,List.of(1,2,3,4,5,6,7,8,9,10));
        edit2.set(animal2 ,List.of(11,12,13,14,15,16,17,18,19,20));
        List<Integer> FromMatingGenom = genomHandler.generateGenomByMating(animal1, animal2);
        System.out.println(FromMatingGenom);
    }

}