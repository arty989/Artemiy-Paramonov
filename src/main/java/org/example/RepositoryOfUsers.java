package org.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RepositoryOfUsers implements UserRepository {
  private final Map<String, User> usersDatabase = new ConcurrentHashMap<>();

  public User findByMsisdn(String msisdn) throws InvalidMsisdnException{
    if (msisdn == null) {
      throw new InvalidMsisdnException("msisdn не может принимать значение null");
    }
    return usersDatabase.get(msisdn);
  }

  public void updateUserByMsisdn(String msisdn, User user) throws InvalidUserException, InvalidMsisdnException{
    if (user == null) {
      throw new InvalidUserException("user не может принимать значение null");
    }
    if (msisdn == null) {
      throw new InvalidMsisdnException("msisdn не может принимать значение null");
    }
    usersDatabase.put(msisdn, user);
  }
}
