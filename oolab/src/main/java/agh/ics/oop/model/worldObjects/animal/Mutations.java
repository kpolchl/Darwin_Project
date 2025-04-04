package agh.ics.oop.model.worldObjects.animal;

import java.util.List;

public class Mutations {

    public void switchRandomGenes(List<Integer> genome) {
        int gene1Index = (int) (Math.random() * genome.size());
        int gene2Index = (int) (Math.random() * genome.size());
        int temp = genome.get(gene1Index);
        genome.set(gene1Index, genome.get(gene2Index));
        genome.set(gene2Index, temp);
    }

    public void mutateRandomGenes(List<Integer> genome) {
        int gene1Index = (int) (Math.random() * genome.size());
        genome.set(gene1Index, (int) (Math.random() * 8));
    }


}
