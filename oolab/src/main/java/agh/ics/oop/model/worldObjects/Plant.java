package agh.ics.oop.model.worldObjects;

import agh.ics.oop.model.utils.Vector2d;

public class Plant implements WorldElement {
    private final Vector2d position;

    public Plant(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }



}
