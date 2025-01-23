package agh.ics.oop.model.abstracts;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Plant;
import agh.ics.oop.model.Vector2d;

import java.util.HashMap;

public class AbstractMap {
    protected int height;
    protected int width;

    protected HashMap<Vector2d, Plant> plantMap;
    protected int plantAmount;

    protected HashMap<Vector2d, Animal> animalMap;

    AbstractMap(int height, int width, HashMap<Vector2d, Plant> plantMap, int plantAmount) {

        this.height = height;
        this.width = width;

        this.plantMap = plantMap;
        this.plantAmount = plantAmount;

        this.animalMap = new HashMap<>();

    }





}
