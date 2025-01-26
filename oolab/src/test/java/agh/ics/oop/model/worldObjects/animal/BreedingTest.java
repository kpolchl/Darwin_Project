package agh.ics.oop.model.worldObjects.animal;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.animal.Animal;
import agh.ics.oop.model.worldObjects.animal.Breeding;
import agh.ics.oop.model.exceptions.BreedignError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class BreedingTest {

    @Test
    void testBreedSuccessful() throws BreedignError {
        // Ustawienie
        Breeding breeding = new Breeding(4, 10); // ENERGY_PARTITION = 4, BREEDING_ENERGY = 10
        Animal father = new Animal(new Vector2d(0,0), 20, List.of(1, 2, 3, 4, 5));
        Animal mother = new Animal(new Vector2d(0,0), 15, List.of(6, 7, 8, 9, 0));

        // Akcja
        Animal child = breeding.breed(father, mother);

        // Assercje
        assertNotNull(child, "Dziecko powinno zostać stworzone.");
        assertEquals(15, father.getEnergy(), "Ojciec powinien stracić odpowiednią ilość energii.");
        assertEquals(11, mother.getEnergy(), "Matka powinna stracić odpowiednią ilość energii.");
        assertEquals(9, child.getEnergy(), "Dziecko powinno mieć energię równą sumie strat energii rodziców.");
        assertEquals(5, child.getGenome().size(), "Dziecko powinno odziedziczyć genom o poprawnym rozmiarze.");
    }

    @Test
    void testBreedThrowsErrorWhenFatherCannotBreed() {
        Breeding breeding = new Breeding(4, 10); // ENERGY_PARTITION = 4, BREEDING_ENERGY = 10
        Animal father = new Animal(new Vector2d(0, 0), 8, List.of(1, 2, 3, 4, 5));
        Animal mother = new Animal(new Vector2d(0, 0), 15, List.of(6, 7, 8, 9, 0));

        // Akcja i asercje
        assertThrows(BreedignError.class, () -> breeding.breed(father, mother), "Powinien zostać zgłoszony wyjątek dla ojca bez wystarczającej energii.");
    }

    @Test
    void testBreedThrowsErrorWhenMotherCannotBreed() {
        Breeding breeding = new Breeding(4, 10); // ENERGY_PARTITION = 4, BREEDING_ENERGY = 10
        Animal father = new Animal(new Vector2d(0, 0), 20, List.of(1, 2, 3, 4, 5));
        Animal mother = new Animal(new Vector2d(0, 0), 5, List.of(6, 7, 8, 9, 0));

        // Akcja i asercje
        assertThrows(BreedignError.class, () -> breeding.breed(father, mother), "Powinien zostać zgłoszony wyjątek dla matki bez wystarczającej energii.");
    }

    @Test
    void testCalculateEnergyBreedingLoss() {
        Breeding breeding = new Breeding(4, 10); // ENERGY_PARTITION = 4, BREEDING_ENERGY = 10
        Animal animal = new Animal(new Vector2d(0, 0), 20, List.of(1, 2, 3, 4, 5));

        // Metoda prywatna do testowania – trzeba uczynić ją publiczną na potrzeby testów
        int energyLoss = breeding.calculateEnergyBreedingLoss(animal);
        assertEquals(5, energyLoss, "Strata energii powinna być poprawnie wyliczona.");
    }
}
