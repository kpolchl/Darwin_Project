package agh.ics.oop.model.worldObjects;

import agh.ics.oop.model.utils.Vector2d;

public class Plant {
    private final Vector2d position;
    private static final int ENERGY = 5;

    public Plant(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    public static int getPlantEnergy() {
        return ENERGY;
    }

}
