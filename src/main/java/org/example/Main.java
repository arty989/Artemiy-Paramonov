package org.example;

public class Main {
  public static void main(String[] args) {
    Eagle eagle = new Eagle();
    eagle.walk();
    eagle.typeSupply();
    eagle.eat();
    System.out.println("");

    Tiger tiger = new Tiger();
    tiger.walk();
    tiger.typeSupply();
    tiger.eat();
    System.out.println("");

    Dolphin dolphin = new Dolphin();
    dolphin.walk();
    dolphin.typeSupply();
    dolphin.eat();
    System.out.println("");

    Horse horse = new Horse();
    horse.walk();
    horse.typeSupply();
    horse.eat();
    System.out.println("");

    Camel camel = new Camel();
    camel.walk();
    camel.typeSupply();
    camel.eat();
  }
}