package org.example;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MergeSortTest {
  @Test
  void testSortWithValidList() {
    MergeSort mergeSort = new MergeSort(5);
    List<Integer> input = Arrays.asList(5, 3, 8, 1, 2);
    List<Integer> expected = Arrays.asList(1, 2, 3, 5, 8);

    mergeSort.sort(input);

    assertEquals(expected, input);
  }

  @Test
  void testSortWithEmptyList() {
    MergeSort mergeSort = new MergeSort(5);
    List<Integer> input = Arrays.asList();
    List<Integer> expected = Arrays.asList();

    mergeSort.sort(input);

    assertEquals(expected, input);
  }

  @Test
  void testSortThrowsExceptionWhenListSizeExceedsMaxSize() {
    MergeSort mergeSort = new MergeSort(3);
    List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> mergeSort.sort(input)
    );

    assertEquals("Алгоритм сортировки MergeSort не способен обработать 5 элементов.", exception.getMessage());
  }

  @Test
  void testTypeMethod() {
    MergeSort mergeSort = new MergeSort(5);

    assertEquals(SortType.MERGE, mergeSort.type());
  }
}