package org.example;

public class InvalidUserException extends IllegalArgumentException {
  public InvalidUserException (String message) {
    super(message);
  }
}
