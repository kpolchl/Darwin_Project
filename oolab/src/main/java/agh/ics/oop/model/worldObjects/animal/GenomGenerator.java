package agh.ics.oop.model.worldObjects.animal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GenomGenerator {
    private final int GENOM_SIZE = 10;


    public List<Integer> generateRandomGenome() {
        List<Integer> genom = new ArrayList<>();
        for(int i = 0; i < GENOM_SIZE; i++) {
            genom.add((int)(Math.random() * 8));
        }
        return genom;
    }


    public int activateRandomGene() {
        return (int)(Math.random()*GENOM_SIZE);
    }

    public List<Integer> generateGenomeByMating(List<Integer> dominantGenome, List<Integer> submissiveGenome , int energy1 ,int energy2) {

        int genomSize = submissiveGenome.size();
        if(energy2 > energy1) {
            List<Integer> temp = submissiveGenome;
            dominantGenome = submissiveGenome;
            submissiveGenome = temp;
        }

        int leftSplitIndex = (int) (((Math.max(energy1, energy2))/ (double) (energy1+energy2)) * genomSize);
        int rightSplitIndex = (int) (( Math.min(energy1,energy2)/ (double) (energy1+energy2)) * genomSize);

        boolean dominantOnLeft = Math.random() < 0.5;
        if (dominantOnLeft) {
            List<Integer> dominantGenomSublist = dominantGenome.subList(0, leftSplitIndex);
            List<Integer> submissiveGenomSublist = submissiveGenome.subList(leftSplitIndex, genomSize);
            return Stream.concat(dominantGenomSublist.stream(), submissiveGenomSublist.stream()).toList(); // concatenation using stream
        }
        else {
            List<Integer> dominantGenomSublist = dominantGenome.subList(rightSplitIndex, genomSize );
            List<Integer> submissiveGenomSublist = submissiveGenome.subList(0, rightSplitIndex);
            return Stream.concat(submissiveGenomSublist.stream(), dominantGenomSublist.stream()).toList();
        }
    }

}
