package marxbank.API;

import marxbank.model.Account;
import marxbank.model.CheckingAccount;
import marxbank.model.CreditAccount;
import marxbank.model.MarxAccount;
import marxbank.model.SavingsAccount;
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

    public Account buildAccount() {
        Account a = null;

        if (type == AccountType.SAVING) {
            a = new SavingsAccount();
            a.setName(name);
            a.setInterestRate(3.0);
            a.setType(type);
        } else if (type == AccountType.CHECKING) {
            a = new CheckingAccount();
            a.setName(name);
            a.setInterestRate(0.5);
            a.setType(type);
        } else if (type == AccountType.CREDIT) {
            a = new CreditAccount();
            a.setName(name);
            a.setInterestRate(0);
            a.setType(type);
        } else if (type == AccountType.MARX) {
            a = new MarxAccount();
            a.setName(name);
            a.setInterestRate(0.01);
            a.setType(type);
        }

        return a;
    }
}
