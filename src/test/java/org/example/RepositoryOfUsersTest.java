package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RepositoryOfUsersTest {
  private RepositoryOfUsers repository;

  @BeforeEach
  void setUp() {
    this.repository = new RepositoryOfUsers();
  }

  @Test
  void testUpdateAndFindByMsisdn() {
    User user = new User("Artemiy", "Paramonov");
    String msisdn = "88005553535";
    repository.updateUserByMsisdn(msisdn, user);
    User findUser = repository.findByMsisdn(msisdn);
    assertEquals(user, findUser);
  }

  @Test
  void testFindByNullMsisdn() {
    IllegalArgumentException exception = assertThrows(InvalidMsisdnException.class, () -> {
      repository.findByMsisdn(null);
    });

    assertEquals("msisdn не может принимать значение null", exception.getMessage());
  }

  @Test
  void testUpdateNullUserByMsisdn() {
    IllegalArgumentException exception = assertThrows(InvalidUserException.class, () -> {
      repository.updateUserByMsisdn("88005553535", null);
    });
    assertEquals("user не может принимать значение null", exception.getMessage());
  }

  @Test
  void testUpdateUserByNullMsisdn() {
    User user = new User("Artemiy", "Paramonov");
    IllegalArgumentException exception = assertThrows(InvalidMsisdnException.class, () -> {
    repository.updateUserByMsisdn(null, user);
    });
    assertEquals("msisdn не может принимать значение null", exception.getMessage());
  }
}