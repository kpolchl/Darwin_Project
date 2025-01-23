package agh.ics.oop.model.animal;

import java.util.ArrayList;
import java.util.List;

public class Mutations {

    public  void switchRandomGenes(List<Integer> genome) {
        int gene1Index = (int)(Math.random() * genome.size());
        int gene2Index = (int)(Math.random() * genome.size());
        System.out.println(gene1Index);
        System.out.println(gene2Index);
        int temp = genome.get(gene1Index);
        genome.set(gene1Index, genome.get(gene2Index));
        genome.set(gene2Index, temp);
    }


}
