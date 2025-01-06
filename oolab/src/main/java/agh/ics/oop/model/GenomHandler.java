package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GenomHandler {
    private final int ENERGY_PARTITION =3;

    // random genom for newly created animals, only on the simulation start
    public List<Integer>  generateRandomGenom(int size) {
         List<Integer> genom = new ArrayList<>();
         for(int i = 0; i < size; i++) {
             genom.add((int)(Math.random() * 8));
         }
         return genom;
    }

    public List<Integer> generateGenomByMating(Animal dominant, Animal submissive) {
        List<Integer> domimantGenom = dominant.getGenome();
        List<Integer> submisiveGenom = submissive.getGenome();
        int genomSize = domimantGenom.size();

        // getting int ceiling the way I found on stack overflow int n = (a - 1) / b + 1; doesn't work for a=0 and b<1
        int dominantEnergy = (dominant.getEnergy()-1)/ENERGY_PARTITION+1;
        int submissiveEnergy = (submissive.getEnergy()-1)/ENERGY_PARTITION+1;

        int newEnergy = dominantEnergy + submissiveEnergy;

        int leftSplitIndex = (int) (( dominantEnergy/ (double) newEnergy) * genomSize);
        int rightSplitIndex = (int) (( submissiveEnergy/ (double) newEnergy) * genomSize);
        boolean dominantOnLeft = Math.random() < 0.5;
        if (dominantOnLeft) {
            List<Integer> dominantGenomSublist = domimantGenom.subList(0, leftSplitIndex);
            List<Integer> submissiveGenomSublist = submisiveGenom.subList(leftSplitIndex, genomSize);
            return Stream.concat(dominantGenomSublist.stream(), submissiveGenomSublist.stream()).toList(); // concatenation using stream
        }
        else {
            List<Integer> dominantGenomSublist = domimantGenom.subList(rightSplitIndex, genomSize );
            List<Integer> submissiveGenomSublist = submisiveGenom.subList(0, rightSplitIndex);
            return Stream.concat(submissiveGenomSublist.stream(), dominantGenomSublist.stream()).toList();
        }
    }

}
