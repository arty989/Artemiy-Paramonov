package org.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EnrichmentService {
  private final Map<EnrichmentType, Enrichment> enrichments;

  public EnrichmentService() {
    this.enrichments = new ConcurrentHashMap<>();
  }
  public EnrichmentService(Map<EnrichmentType, Enrichment> enrichments) {
    this.enrichments = new ConcurrentHashMap<>(enrichments);
  }

  public void addEnrichment(EnrichmentType type, Enrichment enrichment) throws InvalidEnrichmentTypeException, InvalidEnrichmentException {
    if (type == null) {
      throw new InvalidEnrichmentTypeException("Тип обогащения не может принимать значение null");
    }
    if (enrichment == null) {
      throw new InvalidEnrichmentException("Алгоритм обогащения не может быть null");
    }
    enrichments.put(type,enrichment);
  }

  public Message enrich(Message message) throws InvalidEnrichmentTypeException {
    Enrichment enrichment = enrichments.get(message.enrichmentType());
    if (enrichment != null) {
      Map<String, String> content = message.content();
      content = enrichment.enrich(content);
      return new Message(content, message.enrichmentType());
    }
    throw new InvalidEnrichmentTypeException("Не найдено алгоритма, способного обогатить сообщение методом " + message.enrichmentType());
  }
}
