package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnrichmentServiceTest {
  private EnrichmentService enrichmentService;
  private RepositoryOfUsers repository;
  
  @BeforeEach
  void setUp() {
    this.enrichmentService = new EnrichmentService();
    this.repository = new RepositoryOfUsers();
    User user = new User("Artemiy", "Paramonov");
    repository.updateUserByMsisdn("88005553535", user);
  }

  @Test
  void testAddNullTypeEnrichment() {
    IllegalArgumentException exception = assertThrows(InvalidEnrichmentTypeException.class, () -> {
      enrichmentService.addEnrichment(null, new EnrichmentByMsisdn(repository));
    });

    assertEquals("Тип обогащения не может принимать значение null", exception.getMessage());
  }

  @Test
  void testAddNullEnrichment() {
    IllegalArgumentException exception = assertThrows(InvalidEnrichmentException.class, () -> {
      enrichmentService.addEnrichment(EnrichmentType.MSISDN, null);
    });

    assertEquals("Алгоритм обогащения не может быть null", exception.getMessage());
  }

  @Test
  void testNotFoundEnrichment() {
    Message message = new Message(Map.of("one", "two"), EnrichmentType.MSISDN);
    IllegalArgumentException exception = assertThrows(InvalidEnrichmentTypeException.class, () -> {
      enrichmentService.enrich(message);
    });

    assertEquals("Не найдено алгоритма, способного обогатить сообщение методом " + EnrichmentType.MSISDN, exception.getMessage());
  }

  @Test
  void testEnrich() {
    enrichmentService.addEnrichment(EnrichmentType.MSISDN, new EnrichmentByMsisdn(repository));
    Map<String, String> inputContent = new HashMap<>();
    inputContent.put("msisdn", "88005553535");
    Message message = new Message(inputContent, EnrichmentType.MSISDN);
    Message enrichMessage = enrichmentService.enrich(message);
    Message expectedMessage = new Message(Map.of(
    "msisdn", "88005553535",
    "firstName", "Artemiy",
    "lastName", "Paramonov"
    ), EnrichmentType.MSISDN);
    assertEquals(enrichMessage, expectedMessage);
  }
}