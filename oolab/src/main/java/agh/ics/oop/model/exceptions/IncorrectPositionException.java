package agh.ics.oop.model.exceptions;

import agh.ics.oop.model.utils.Vector2d;

public class IncorrectPositionException extends Exception {
  public IncorrectPositionException(Vector2d position) {
    super("Position"+position+"is out of bounds");
  }
}
