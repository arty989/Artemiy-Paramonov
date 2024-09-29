package org.example;

public class Predators extends Animals {
  protected Predators (String animalType) {
    super(animalType);
  }
  protected void eat (Food food) {
    super.eat(food);
  }
  protected void walk (String how) {
    super.walk(how);
  }
  protected void typeSupply () {
    super.typeSupply("хищник");
  }
}
