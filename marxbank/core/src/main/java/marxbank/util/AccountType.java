package marxbank.util;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum AccountType {
  SAVING("Sparekonto"), CHECKING("Brukskonto"), MARX("Marxkonto"), CREDIT("Kredittkonto"),;

  private final String typeString;

  // Reverse-lookup map for getting a account type from a string
  private static final Map<String, AccountType> lookup = new HashMap<String, AccountType>();

  AccountType(String type) {
    this.typeString = type;
  }

  static {
    for (AccountType t : AccountType.values()) {
      lookup.put(t.getTypeString(), t);
    }
  }


  public String getTypeString() {
    return this.typeString;
  }

  public static Stream<AccountType> stream() {
    return Stream.of(AccountType.values());
  }

  public static AccountType get(String type) {
    return lookup.get(type);
  }

}
