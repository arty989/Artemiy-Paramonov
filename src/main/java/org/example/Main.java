package org.example;

import java.util.Map;
import java.util.HashMap;

public class Main {
  public static void main(String[] args) {
    User user = new User("Artemiy", "Paramonov");
    RepositoryOfUsers repository = new RepositoryOfUsers();
    repository.updateUserByMsisdn("88005553535", user);
    Map<String, String> content = new HashMap<>();
    content.put("action", "button_click");
    content.put("page", "book_card");
    content.put("msisdn", "88005553535");
    Message message = new Message(content, EnrichmentType.MSISDN);

    EnrichmentService enrichmentService = new EnrichmentService();
    enrichmentService.addEnrichment(EnrichmentType.MSISDN, new EnrichmentByMsisdn(repository));

    Message enrichMessage = enrichmentService.enrich(message);

    for (String element : enrichMessage.content().keySet()) {
      System.out.println(element + " : " + enrichMessage.content().get(element));
    }
    System.out.println();
    User friend = new User("Sasha", "Beliy");
    repository.updateUserByMsisdn("88005553535", friend);

    Map<String, String> friendContent = new HashMap<>();
    friendContent.put("msisdn", "88005553535");
    Message friendMessage = new Message(friendContent, EnrichmentType.MSISDN);
    Message friendEnrichMessage = enrichmentService.enrich(friendMessage);
    for (String element : friendEnrichMessage.content().keySet()) {
      System.out.println(element + " : " + friendEnrichMessage.content().get(element));
    }
  }
}