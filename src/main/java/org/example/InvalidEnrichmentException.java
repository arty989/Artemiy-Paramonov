package org.example;

public class InvalidEnrichmentException extends IllegalArgumentException{
  public InvalidEnrichmentException(String message) {
    super(message);
  }
}
