package org.example;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BubbleSortTest {
  @Test
  void testSortWithValidList() {
    BubbleSort bubbleSort = new BubbleSort(5);
    List<Integer> input = Arrays.asList(5, 3, 8, 1, 2);
    List<Integer> expected = Arrays.asList(1, 2, 3, 5, 8);

    bubbleSort.sort(input);

    assertEquals(expected, input);
  }

  @Test
  void testSortWithEmptyList() {
    BubbleSort bubbleSort = new BubbleSort(5);
    List<Integer> input = Arrays.asList();
    List<Integer> expected = Arrays.asList();

    bubbleSort.sort(input);

    assertEquals(expected, input);
  }

  @Test
  void testSortThrowsExceptionWhenListSizeExceedsMaxSize() {
    BubbleSort bubbleSort = new BubbleSort(3);
    List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> bubbleSort.sort(input)
    );

    assertEquals("Алгоритм сортировки BubbleSort не способен обработать 5 элементов.", exception.getMessage());
  }

  @Test
  void testTypeMethod() {
    BubbleSort bubbleSort = new BubbleSort(5);

    assertEquals(SortType.BUBBLE, bubbleSort.type());
  }
}