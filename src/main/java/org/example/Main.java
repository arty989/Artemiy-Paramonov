package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws Exception {
    Scanner input = new Scanner(System.in);
    Sorter sorter = new Sorter(
      List.of(
        new MergeSort(10),
        new BubbleSort(3)
      )
    );

    System.out.print("Введите длину списка: ");
    int len = input.nextInt();
    System.out.print("Через пробел введите список: ");
    List<Integer> array = new ArrayList<>();
    for (int i = 0; i < len; i++) {
      array.add(input.nextInt());
    }
    System.out.println("В данный момент доступны следующие типы сортировок:");
    for (SortType element : SortType.values()) {
      System.out.print(element + " ");
    }

    System.out.print("\nВыберите необходимую и введите её: ");
    SortType type = SortType.valueOf(input.next());

    List<Integer> sortedList = sorter.sort(array, type);

    System.out.print("Исходный список: ");
    for (int element : array) {
      System.out.print(element + " ");
    }

    System.out.print("\nОтсортированный список: ");
    for (int element : sortedList) {
      System.out.print(element + " ");
    }
  }
}