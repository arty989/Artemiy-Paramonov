package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationTest {
  @Test
  void shouldReturnEnrichedMessage() {
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
    Message expectedMessage = new Message(Map.of(
    "action", "button_click",
    "page", "book_card",
    "msisdn", "88005553535",
    "firstName", "Artemiy",
    "lastName", "Paramonov"
    ), EnrichmentType.MSISDN);

    assertEquals(enrichMessage, expectedMessage);

    System.out.println();
    User friend = new User("Sasha", "Beliy");
    repository.updateUserByMsisdn("88005553535", friend);

    Map<String, String> friendContent = new HashMap<>();
    friendContent.put("msisdn", "88005553535");
    Message friendMessage = new Message(friendContent, EnrichmentType.MSISDN);
    Message friendEnrichMessage = enrichmentService.enrich(friendMessage);

    Message expectedFriendMessage = new Message(Map.of(
    "msisdn", "88005553535",
    "firstName", "Sasha",
    "lastName", "Beliy"
    ), EnrichmentType.MSISDN);

    assertEquals(friendEnrichMessage, expectedFriendMessage);
  }

  @Test
  void shouldSucceedEnrichmentInConcurrentEnvironmentSuccessfully() throws InterruptedException {
    RepositoryOfUsers repository = new RepositoryOfUsers();
    repository.updateUserByMsisdn("01", new User("Artemiy", "Paramonov"));
    repository.updateUserByMsisdn("02", new User("Ivan", "Abramov"));
    repository.updateUserByMsisdn("03", new User("Petya", "Ivanov"));
    repository.updateUserByMsisdn("04", new User("Vasya", "Kruglov"));
    repository.updateUserByMsisdn("789", new User("Vladimir", "Kurenkov"));

    EnrichmentService enrichmentService = new EnrichmentService(Map.of(EnrichmentType.MSISDN, new EnrichmentByMsisdn(repository)));


    List<Message> expected = new CopyOnWriteArrayList<>(List.of(
    new Message(Map.of(
    "test", "1",
    "msisdn", "01",
    "firstName", "Artemiy",
    "lastName", "Paramonov"), EnrichmentType.MSISDN),

    new Message(Map.of(
    "test", "2",
    "msisdn", "02",
    "firstName", "Ivan",
    "lastName", "Abramov"), EnrichmentType.MSISDN),

    new Message(Map.of(
    "test", "3",
    "msisdn", "03",
    "firstName", "Petya",
    "lastName", "Ivanov"), EnrichmentType.MSISDN),

    new Message(Map.of(
    "test", "4",
    "msisdn", "04",
    "firstName", "Vasya",
    "lastName", "Kruglov"), EnrichmentType.MSISDN),

    new Message(Map.of(
    "test", "5",
    "msisdn", "05"), EnrichmentType.MSISDN))
    );


    List<Message> messages = new CopyOnWriteArrayList<>(List.of(
    new Message(Map.of(
    "test", "1",
    "msisdn", "01"), EnrichmentType.MSISDN),

    new Message(Map.of(
    "test", "2",
    "msisdn", "02"), EnrichmentType.MSISDN),

    new Message(Map.of(
    "test", "3",
    "msisdn", "03"), EnrichmentType.MSISDN),

    new Message(Map.of(
    "test", "4",
    "msisdn", "04"), EnrichmentType.MSISDN),

    new Message(Map.of(
    "test", "5",
    "msisdn", "05"), EnrichmentType.MSISDN))
    );

    List<Message> enrichmentResults = new CopyOnWriteArrayList<>();
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    CountDownLatch latch = new CountDownLatch(5);
    for (int i = 0; i < 5; i++) {
      Message message = messages.get(i);
      final int position = i;
      executorService.submit(() -> {
        enrichmentResults.add(position,
        enrichmentService.enrich(message)
        );
        latch.countDown();
      });
    }
    latch.await();
    for (int i = 0; i < 5; i++) {
      Message expectedMessage = expected.get(i);
      Message actualMessage = enrichmentResults.get(i);
      assertEquals(expectedMessage.content(), actualMessage.content());
      assertEquals(expectedMessage.enrichmentType(), actualMessage.enrichmentType());
    }
  }
}
