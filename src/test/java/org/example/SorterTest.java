package org.example;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SorterTest {
  @Test
  void testSortThrowsExceptionWhenAlgorithmIsNull() throws IllegalArgumentException {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Sorter(null));
    assertEquals("null не может быть методом сортировки.",exception.getMessage());
  }

  @Test
  void testSortThrowsExceptionWhenAlgorithmNotFound () {
    Sorter sorter = new Sorter(
      List.of(
        new MergeSort(10),
        new BubbleSort(3)
      )
    );
    AlgorithmNotFoundException exception = assertThrows(AlgorithmNotFoundException.class, () -> sorter.sort(Arrays.asList(5, 4, 3, 2, 1), SortType.BUBBLE));
    assertEquals("Не найден алгоритм типа сортировки BUBBLE способный отсортировать список длины 5", exception.getMessage());
  }

}