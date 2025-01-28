package agh.ics.oop.model.worldObjects.animal;

import agh.ics.oop.model.worldObjects.animal.GenomGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenomeGeneratorTest {

    @Test
    void testGenerateRandomGenome() {
        GenomGenerator generator = new GenomGenerator();
        List<Integer> genome = generator.generateRandomGenome(10);

        // Sprawdź rozmiar genomu
        assertEquals(10, genome.size(), "Genom powinien mieć dokładnie 10 genów.");

        // Sprawdź czy geny mieszczą się w zakresie [0, 7]
        for (Integer gene : genome) {
            assertTrue(gene >= 0 && gene <= 7, "Geny powinny mieścić się w zakresie od 0 do 7.");
        }
    }

    @Test
    void testActivateRandomGene() {
        GenomGenerator generator = new GenomGenerator();
        int activatedGene = generator.activateRandomGene(10);
        System.out.println(activatedGene);
        // Sprawdź czy aktywowany gen jest w zakresie [0, 9]
        assertTrue(activatedGene >= 0 && activatedGene < 10, "Aktywowany gen powinien być w zakresie od 0 do 9.");
    }

    @Test
    void testGenerateGenomeByMatingDominantOnLeft() {
        GenomGenerator generator = new GenomGenerator();

        List<Integer> dominantGenome = List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        List<Integer> submissiveGenome = List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

        // Wyższa energia dla dominującego genomu
        List<Integer> resultGenome = generator.generateGenomeByMating(dominantGenome, submissiveGenome, 70, 30);
        System.out.println(resultGenome);

        // Rozmiar genomu powinien być zgodny
        assertEquals(10, resultGenome.size(), "Genom potomka powinien mieć 10 genów.");

        // Sprawdź części genomów (dominujący na lewej części)
        for (int i = 0; i < resultGenome.size(); i++) {
            if (i < 7) { // Zakładamy 70% dominujący
                assertEquals(0, resultGenome.get(i), "Lewa część powinna być zgodna z genomem dominującym.");
            } else {
                assertEquals(1, resultGenome.get(i), "Prawa część powinna być zgodna z genomem podporządkowanym.");
            }
        }
    }

    @Test
    void testGenerateGenomeByMatingDominantOnRight() {
        GenomGenerator generator = new GenomGenerator();

        List<Integer> dominantGenome = List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        List<Integer> submissiveGenome = List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

        // Wyższa energia dla dominującego genomu
        List<Integer> resultGenome = generator.generateGenomeByMating(dominantGenome, submissiveGenome, 30, 70);
        System.out.println(resultGenome);

        // Rozmiar genomu powinien być zgodny
        assertEquals(10, resultGenome.size(), "Genom potomka powinien mieć 10 genów.");

        // Sprawdź części genomów (dominujący na prawej części)
        for (int i = 0; i < resultGenome.size(); i++) {
            if (i < 3) { // Zakładamy 30% dominujący
                assertEquals(0, resultGenome.get(i), "Lewa część powinna być zgodna z genomem podporządkowanym.");

            } else {
                assertEquals(1, resultGenome.get(i), "Prawa część powinna być zgodna z genomem dominującym.");
            }
        }
    }
}
