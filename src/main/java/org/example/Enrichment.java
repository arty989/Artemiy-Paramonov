package org.example;

import java.util.Map;

public interface Enrichment {
  EnrichmentType type();

  Map<String, String> enrich(Map<String, String> input);
}
