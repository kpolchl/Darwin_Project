package agh.ics.oop.model.worldObjects.animal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenomGenerator {

    public List<Integer> generateRandomGenome(int genomeSize) {
        List<Integer> genom = new ArrayList<>();
        for (int i = 0; i < genomeSize; i++) {
            genom.add((int) (Math.random() * 8));
        }
        return genom;
    }

    public int activateRandomGene(int genomeSize) {
        return (int) ((Math.random()) * genomeSize);
    }

    public List<Integer> generateGenomeByMating(List<Integer> dominantGenome, List<Integer> submissiveGenome, int energy1, int energy2) {
        int genomSize = submissiveGenome.size();

        // Swap genomes if energy2 > energy1
        if (energy2 > energy1) {
            List<Integer> temp = submissiveGenome;
            submissiveGenome = dominantGenome;
            dominantGenome = temp;
        }

        // Calculate split indices based on energy ratios
        int leftSplitIndex = (int) (((Math.max(energy1, energy2)) / (double) (energy1 + energy2)) * genomSize);
        int rightSplitIndex = (int) ((Math.min(energy1, energy2) / (double) (energy1 + energy2)) * genomSize);

        // Randomly decide which genome is on the left
        boolean dominantOnLeft = Math.random() < 0.5;

        // Concatenate the sublists into a list
        if (dominantOnLeft) {
            List<Integer> dominantGenomSublist = dominantGenome.subList(0, leftSplitIndex);
            List<Integer> submissiveGenomSublist = submissiveGenome.subList(leftSplitIndex, genomSize);
            return Stream.concat(dominantGenomSublist.stream(), submissiveGenomSublist.stream())
                    .collect(Collectors.toCollection(ArrayList::new));
        } else {
            List<Integer> dominantGenomSublist = dominantGenome.subList(rightSplitIndex, genomSize);
            List<Integer> submissiveGenomSublist = submissiveGenome.subList(0, rightSplitIndex);
            return Stream.concat(submissiveGenomSublist.stream(), dominantGenomSublist.stream())
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

}
