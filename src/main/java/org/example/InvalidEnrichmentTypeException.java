package org.example;

public class InvalidEnrichmentTypeException extends IllegalArgumentException {
  public InvalidEnrichmentTypeException(String message) {
    super(message);
  }
}
