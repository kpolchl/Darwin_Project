package agh.ics.oop.model.worldObjects.animal;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MutationsTest {

    @Test
    void doesSwitchingGenesWork() {
        //given
        List<Integer> genome = new ArrayList<>();
        genome.add(1);
        genome.add(2);
        genome.add(3);
        genome.add(4);
        genome.add(5);
        genome.add(6);
        genome.add(7);
        genome.add(8);
        genome.add(9);
        genome.add(10);
        System.out.println(genome.toString());
        //when
        Mutations mutations = new Mutations();
        mutations.switchRandomGenes(genome);
        //then
        System.out.println(genome.toString()); //czy 2 liczby są zamienione
    }
}