package marxbank.API;

import marxbank.AccountFactory;
import marxbank.model.Account;
import marxbank.util.AccountType;

/**
 * Klassen holder på informasjonen frontenden må formidle fra brukeren når de ønsker å oppprette en ny bankkonto. 
 * Den esensielle informasjonen for dette er type konto og kontonavn.
 */
public class AccountRequest {
  private String type;
  private String name;

/**
 * Tom konstruktør.
 */
    protected AccountRequest() {}

/**
 * Konstruktør for AccountRequest.
 * @param type - kontotype
 * @param name - kontonavn
 */
    public AccountRequest(String type, String name) {
        this.type = type;
        this.name = name;
    }

/**
 * Getters og setters.
 */
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

/**
 * En metode for å bygge en ny konto ved å bruke input-parameterne og sette all den nødvendige kontoinformasjonen ut i fra dette.
 * @return - en ny konto
 */
    public Account buildAccount() {
       return AccountFactory.create(AccountType.get(this.getType()), this.getName());
    }
}
