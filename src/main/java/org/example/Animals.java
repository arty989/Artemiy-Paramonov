package org.example;

public abstract class Animals {
  private final String animalType;

  protected Animals(String animalType) {
    this.animalType = animalType;
  }

  protected void eat(Food food) {
    System.out.println(animalType + " ест " + food.type);
  }

  protected void walk(String how) {
    System.out.println(animalType + how);
  }

  protected void typeSupply (String type) {
    System.out.println(animalType + " " + type);
  }
}
