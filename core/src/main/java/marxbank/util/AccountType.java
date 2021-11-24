package marxbank.util;

import java.util.stream.Stream;

public enum AccountType {
  SAVING("Sparekonto"), CHECKING("Brukskonto"), MARX("Marxkonto"), CREDIT("Kredittkonto"),;

  private final String typeString;

  AccountType(String type) {
    this.typeString = type;
  }

  public String getTypeString() {
    return this.typeString;
  }

  public static Stream<AccountType> stream() {
    return Stream.of(AccountType.values());
  }
}
