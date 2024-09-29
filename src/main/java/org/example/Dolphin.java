package org.example;

public class Dolphin extends Predators {
  public Dolphin() {
    super("Дельфин");
  }
  public void eat() {
    super.eat(new Fish());
  }

  public void walk() {
    super.walk(" плавает");
  }
}
