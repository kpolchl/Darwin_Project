package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static agh.ics.oop.model.Animal.*;
import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void generateRandomGenome() {
        List<Integer> randomGenome = Animal.generateRandomGenome(10);
        System.out.println(randomGenome);
    }
    // hmm it's random so difficult to test but it works
    @Test
    void isMatingGenomeValid(){
        List<Integer> genome1 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> genome2 = List.of(11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        int energy1 =200;
        int energy2 =100;
        List<Integer> newGenome = generateGenomeByMating(genome1, genome2, energy1, energy2);
        System.out.println(newGenome);
    }
    @Test
    void isRandomDirectionValid() {
        Animal animal1 = new Animal(new Vector2d(1,1));
        System.out.println(randomDirection());
    }
    @Test
    void doesGenesSwap(){
        List<Integer> genome1 = Arrays.asList(0,1,2,3,4,5,6,7,8,9);
        switchRandomGenes(genome1);
        System.out.println(genome1);
    }


}