package it1901;

public class AccountFactory {
    
    public Account create(String accountType) {
        if ("Sparekonto".equalsIgnoreCase(accountType)) {
            return null;
        } else if ("Duck".equalsIgnoreCase(accountType)) {
            return null;
        }

        return null;
    }
    
}
