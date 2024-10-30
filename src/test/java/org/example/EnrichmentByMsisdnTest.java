package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnrichmentByMsisdnTest {
  private RepositoryOfUsers repository;

  @BeforeEach
  void setUp() {
    this.repository = new RepositoryOfUsers();
    User user = new User("Artemiy", "Paramonov");
    this.repository.updateUserByMsisdn("88005553535", user);
  }

  @Test
  void testEnrichmentWithChanges() {
    EnrichmentByMsisdn enrichment = new EnrichmentByMsisdn(this.repository);
    Map<String, String> inputContent = new HashMap<>();
    inputContent.put("msisdn", "88005553535");
    Map<String, String> outputContent = enrichment.enrich(inputContent);
    Map<String, String> expectedContent = Map.of(
    "msisdn", "88005553535",
    "firstName", "Artemiy",
    "lastName", "Paramonov"
    );
    assertEquals(outputContent, expectedContent);
  }

  @Test
  void testEnrichmentWithNoChanges() {
    EnrichmentByMsisdn enrichment = new EnrichmentByMsisdn(this.repository);
    Map<String, String> inputContent = new HashMap<>();
    inputContent.put("msisdn", "12345678900");
    Map<String, String> outputContent = enrichment.enrich(inputContent);
    Map<String, String> expectedContent = Map.of(
    "msisdn", "12345678900"
    );
    assertEquals(outputContent, expectedContent);
  }
}