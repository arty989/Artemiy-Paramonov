package org.example;

public class Main {
  public static void main(String[] args) {
    //Полная проверка для Integer
    CustomArrayList<Integer> list = new CustomArrayList<>();
    System.out.println(list.isEmpty());
    try {
      list.add(null);
    } catch (NullPointerException e) {
      System.out.println("Поймали ошибку " + e);
    }
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(0, 1);
    if (!list.isEmpty())
      System.out.println("Размер массива " + list.size());
    System.out.println("Удалили " + list.remove(1));
    System.out.println("Теперь массив выглядит так: " + list.get(0) + " " + list.get(1) + " " + list.get(2));
    try {
      list.add(100,2);
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Поймали ошибку " + e);
    }

    //Проверка, что с другими типами тоже работает
    CustomArrayList<String> listString = new CustomArrayList<>();
    listString.add("МТС");
    System.out.println(listString.remove(0));
    System.out.println(listString.isEmpty());
  }
}
