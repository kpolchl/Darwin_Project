package agh.ics.oop.model.observators;

import agh.ics.oop.model.worldMap.AbstractWorldMap;

public interface MapChangeListener {
    void mapChanged(AbstractWorldMap worldMap, String message);
}