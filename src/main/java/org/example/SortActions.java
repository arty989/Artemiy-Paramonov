package org.example;

import java.util.List;

public interface SortActions {
  SortType type();
  List<Integer> sort(List<Integer> list) throws IllegalAccessError;
}
