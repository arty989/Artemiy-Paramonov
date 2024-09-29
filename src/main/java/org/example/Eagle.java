package org.example;

public class Eagle extends Predators {
  public Eagle() {
    super("Орёл");
  }
  public void eat() {
    super.eat(new Meat("любое мясо"));
  }

  public void walk() {
    super.walk(" летает");
  }
}
