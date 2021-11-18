package marxbank.API;

import marxbank.model.Account;
import marxbank.util.AccountType;

public class PublicAccountResponse {
    private Long id;
    private int accountNumber;
    private Long userId;
    private String type;
    private String name;

    protected PublicAccountResponse() {}

    public PublicAccountResponse(Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.userId = account.getUser().getId();
        this.type = account.getAccountType();
        this.name = account.getName();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setAccountNumber(int an) {
        this.accountNumber = an;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setName(String newName){
        this.name = newName;
    }
    
    public String getName(){
        return this.name;
    }

    public void setType(AccountType newType){
        this.type = newType.getTypeString();
    }

    public String getType(){
        return this.type;
    }
}
