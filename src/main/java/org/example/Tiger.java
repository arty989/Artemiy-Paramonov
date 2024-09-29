package org.example;

public class Tiger extends Predators {
  public Tiger() {
    super("Тигр");
  }
  public void eat() {
    super.eat(new Beef());
  }

  public void walk() {
    super.walk(" ходит по суше");
  }
}
