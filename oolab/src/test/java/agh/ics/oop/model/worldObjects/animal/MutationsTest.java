package agh.ics.oop.model.worldObjects.animal;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MutationsTest {

    @Test
    void switchRandomGenes_shouldSwapTwoGenes() {
        // Arrange
        Mutations mutations = new Mutations();
        List<Integer> genome = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

        // Act
        mutations.switchRandomGenes(genome);

        // Assert
        assertEquals(10, genome.size(), "Genome size should remain the same after swapping genes.");
        // Verify that at least one change occurred
        boolean swapped = false;
        for (int i = 0; i < genome.size(); i++) {
            if (genome.get(i) != i) {
                swapped = true;
                break;
            }
        }
        assertTrue(swapped, "At least one gene should be swapped.");
    }

}
