package org.example;

import java.util.Collections;
import java.util.List;

public class MergeSort implements SortActions {
  final private int MAX_SIZE;
  public MergeSort(int size) {
    this.MAX_SIZE = size;
  }

  @Override
  public SortType type() {
    return SortType.MERGE;
  }

  @Override
  public List<Integer> sort(List<Integer> list) throws IllegalArgumentException{
    if (this.MAX_SIZE < list.size()) {
      throw new IllegalArgumentException("Алгоритм сортировки " + this.getClass().getSimpleName() + " не способен обработать " + list.size() + " элементов.");
    }

    Collections.sort(list);
    return list;
  }
}