package agh.ics.oop.model.worldMap;

import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.model.worldObjects.animal.Animal;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class CrawlingJungleMap extends AbstractWorldMap {




    public CrawlingJungleMap(Vector2d MAX_COORD, List<Animal> animals) {
        super(MAX_COORD, animals);
    }

    @Override
    void grassGrow(int N) {

    }
}
