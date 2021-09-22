package it1901.util;

import java.util.stream.Stream;

public enum AccountType {
    SAVING("Sparekonto"),
    ;

    private String typeString;

    AccountType(String type) {
        this.typeString=type;
    }

    public String getTypeString() {
        return this.typeString;
    }

    public static Stream<AccountType> stream() {
        return Stream.of(AccountType.values()); 
    }
}
