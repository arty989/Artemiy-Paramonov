package org.example;

public class InvalidMsisdnException extends IllegalArgumentException {
  public InvalidMsisdnException(String message) {
    super(message);
  }
}
