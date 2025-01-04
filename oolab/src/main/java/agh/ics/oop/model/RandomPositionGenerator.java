package agh.ics.oop.model;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    int MaxHeight;
    int MaxWidth;
    int N;
    List<Vector2d> positions;

    public RandomPositionGenerator(int maxHeight, int maxWidth,int N) {
        this.MaxHeight = maxHeight;
        this.MaxWidth = maxWidth;
        this.N = N;
        positions = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                positions.add(new Vector2d(i,j));
            }
        }
        Collections.shuffle(positions);
    }
    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<Vector2d>() {
            private int generatedCount = 0;

            @Override
            public boolean hasNext() {
                return generatedCount < N;
            }

            @Override
            public Vector2d next() {
                if (!hasNext()) {
                    throw new UnsupportedOperationException("No more positions to generate");
                }

                Vector2d position = positions.get(generatedCount);
                generatedCount++;
                return position;
            }
        };
    }



}
