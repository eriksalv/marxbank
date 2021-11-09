package marxbank.API;

import marxbank.model.Account;
import marxbank.model.CheckingAccount;
import marxbank.model.CreditAccount;
import marxbank.model.MarxAccount;
import marxbank.model.SavingsAccount;
import marxbank.util.AccountType;

public class AccountRequest {
    private String type;
    private String name;

    protected AccountRequest() {}

    public AccountRequest(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
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

        if (AccountType.SAVING.getTypeString().equalsIgnoreCase(type)) {
            a = new SavingsAccount();
            a.setName(name);
            a.setInterestRate(3.0);
            a.setType(AccountType.SAVING);
        } else if (AccountType.CHECKING.getTypeString().equalsIgnoreCase(type)) {
            a = new CheckingAccount();
            a.setName(name);
            a.setInterestRate(0.5);
            a.setType(AccountType.CHECKING);
        } else if (AccountType.CREDIT.getTypeString().equalsIgnoreCase(type)) {
            a = new CreditAccount();
            a.setName(name);
            a.setInterestRate(0);
            a.setType(AccountType.CREDIT);
        } else if (AccountType.MARX.getTypeString().equalsIgnoreCase(type)) {
            a = new MarxAccount();
            a.setName(name);
            a.setInterestRate(0.01);
            a.setType(AccountType.MARX);
        }

        return a;
    }
}
