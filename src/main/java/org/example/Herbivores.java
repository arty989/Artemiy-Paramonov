package org.example;

public class Herbivores extends Animals {
  protected Herbivores(String animalType) {
    super(animalType);
  }

  protected void eat(Food food) {
    super.eat(food);
  }

  protected void walk(String how) {
    super.walk(how);
  }

  protected void typeSupply() {
    super.typeSupply("травоядный");
  }
}
