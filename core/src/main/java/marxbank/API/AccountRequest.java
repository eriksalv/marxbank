package marxbank.API;

import marxbank.util.AccountType;

public class AccountRequest {
    private AccountType type;
    private String name;

    public AccountRequest() {}

    public AccountRequest(AccountType accountType, String name) {
        this.type = accountType;
        this.name = name;
    }

    public AccountType getAccountType() {
        return this.type;
    }

    public void setAccountType(AccountType type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
