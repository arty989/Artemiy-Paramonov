package org.example;

public class Horse extends Herbivores {
  public Horse() {
    super("Лошадь");
  }

  public void eat() {
    super.eat(new Grass());
  }

  public void walk() {
    super.walk(" ходит по суше");
  }
}
