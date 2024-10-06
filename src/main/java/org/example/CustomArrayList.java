package org.example;

/**
 * CustomArrayList - это примитивная реализация класса ArrayList
 * @param <T> тип данных в этом списке
 */
public class CustomArrayList<T> implements Methods<T>{
  /**
   * array - массив, в котором будут находиться элементы
   * capacity - размер массива с значением по умолчанию
   * length - размер заполненной части массива
   */
  private Object[] array;
  private int capacity = 10;
  private int length = 0;

  /**
   * Конструктор CustomArrayList с указанным пользователем размером массива
   * @param capacity - размер массива, введённый пользователем
   */
  public CustomArrayList(int capacity) {
    this.array = new Object[capacity];
    this.capacity = capacity;
  }

  /**
   * Конструктор CustomArrayList c размером массива по умолчанию
   */
  public CustomArrayList() {
    this.array = new Object[this.capacity];
  }

  /**
   * Динамическое расширение структуры данных на случай превышения размера (capacity)
   */
  private void realloc() {
    Object[] newList = new Object[capacity * 2];
    System.arraycopy(this.array, 0, newList, 0, this.length);
    this.array = newList.clone();
    this.capacity *= 2;
  }

  /**
   * Исключение, выбрасываемое, когда введённый пользователем индекс больше размера заполненной части массива (length), или меньше 0
   * @param index - индекс, введённый пользователем
   */
  private void errorIndex(int index) {
    throw new IndexOutOfBoundsException("Index "+index+" out of bounds for length "+length);
  }

  /**
   * Исключение, выбрасываемое, когда введённое пользователем значение равно null
   */
  private void errorValue() {
    throw new NullPointerException("Element can't be null");
  }

  /**
   * Добавляет указанный пользователем элемент в конец массива
   * @param element - указанный пользователем элемент
   */
  public void add(T element) {
    if (element == null) {
      errorValue();
    }
    if (this.length >= this.capacity) {
      realloc();
    }
    this.array[this.length] = element;
    this.length += 1;
  }

  /**
   * Добавляет указанный пользователем элемент на указанную им позицию
   * @param index - индекс (позиция) на который надо вставить элемент
   * @param element - элемент, который необходимо вставить
   */
  @Override
  public void add(int index, T element) {
    if (element == null) {
      errorValue();
    }
    if (index >= length || index < 0){
      errorIndex(index);
    }
    if (this.length >= this.capacity) {
      realloc();
    }
    System.arraycopy(this.array, index, this.array, index + 1, this.length - index);
    this.array[index] = element;
    this.length += 1;
  }

  /**
   * Возвращает пользователю элемент, расположенный в массиве под необходимым индексом
   * @param index - индекс, вводимый пользователем
   * @return элемент, расположенный в массиве под необходимым индексом
   */
  @Override
  public T get(int index) {
    if (index >= length || index < 0){
      errorIndex(index);
    }
    return (T) this.array[index];
  }

  /**
   * Удаляет из массива элемент, расположенный в массиве под необходимым индексом, и возращает его пользователю
   * @param index - индекс, вводимый пользователем
   * @return элемент, удалённый из массива
   */
  @Override
  public T remove(int index) {
    if (index >= length || index < 0){
      errorIndex(index);
    }
    T element = (T) this.array[index];
    System.arraycopy(this.array, index + 1, this.array, index, this.capacity - index - 1);
    this.length -= 1;
    return element;
  }

  /**
   * Возвращает размер заполненной части массива
   * @return размер заполненной части массива
   */
  public int size() {
    return length;
  }

  /**
   * Возвращает true, если массив не заполнен, в противном случае false
   * @return true, если массив не заполнен, в противном случае false
   */
  public boolean isEmpty() {
    return length == 0;
  }
}

