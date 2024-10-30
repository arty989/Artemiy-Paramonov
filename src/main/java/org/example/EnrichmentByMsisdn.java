package org.example;

import java.util.HashMap;
import java.util.Map;

public class EnrichmentByMsisdn implements Enrichment {
  private final UserRepository usersDatabase;

  public EnrichmentByMsisdn(UserRepository usersDatabase) {
    this.usersDatabase = usersDatabase;
  }

  public EnrichmentType type() {
    return EnrichmentType.MSISDN;
  }

  public Map<String, String> enrich(Map<String, String> input) {
    Map<String, String> output = new HashMap<>(input);
    String msisdn = output.get("msisdn");
    if (msisdn == null) {
      return output;
    }
    User findUser = usersDatabase.findByMsisdn(msisdn);
    if (findUser != null) {
      output.put("firstName", findUser.firstName());
      output.put("lastName", findUser.lastName());
    }
    return output;
  }
}
