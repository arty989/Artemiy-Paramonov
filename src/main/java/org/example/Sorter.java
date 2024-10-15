package org.example;

import java.util.ArrayList;
import java.util.List;

public class Sorter {
  private final List<SortActions> actions;

  public Sorter(List<SortActions> actions) throws IllegalArgumentException {
    if (actions == null) {
      throw new IllegalArgumentException("null не может быть методом сортировки.");
    }
    this.actions = actions;
  }

  public List<Integer> sort(List<Integer> oldList, SortType type) throws Exception {
    List<Integer> list = new ArrayList<>(oldList);
    for (SortActions action : actions) {
      if (action.type().equals(type)) {
        try {
          return action.sort(list);
        } catch (IllegalArgumentException e) {
          System.err.println(e.getMessage() + " Перехожу к поиску следующего алгоритма с типом сортировки " + type);
        }
      }
    }
    throw new AlgorithmNotFoundException("Не найден алгоритм типа сортировки " + type + " способный отсортировать список длины " + list.size());
  }
}
