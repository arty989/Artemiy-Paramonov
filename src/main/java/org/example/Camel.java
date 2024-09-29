package org.example;

public class Camel extends Herbivores {
  public Camel () {
    super("Верблюд");
  }
  public void eat() {
    super.eat(new Grass());
  }

  public void walk() {
    super.walk(" ходит по суше");
  }
}
