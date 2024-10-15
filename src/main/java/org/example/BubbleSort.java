package org.example;

import java.util.List;

public class BubbleSort implements SortActions {
  final private int MAX_SIZE;
  public BubbleSort(int size) {
    this.MAX_SIZE = size;
  }

  @Override
  public SortType type() {
    return SortType.BUBBLE;
  }

  @Override
  public List<Integer> sort(List<Integer> list) throws IllegalArgumentException {
    if (this.MAX_SIZE < list.size()) {
      throw new IllegalArgumentException("Алгоритм сортировки " + this.getClass().getSimpleName() + " не способен обработать " + list.size() + " элементов.");
    }

    for (int i = 0; i < list.size(); i++) {
      for (int j = i + 1; j < list.size(); j++) {
        if (list.get(i) > list.get(j)) {
          int temp = list.get(i);
          list.set(i, list.get(j));
          list.set(j, temp);
        }
      }
    }
    return list;
  }
}
