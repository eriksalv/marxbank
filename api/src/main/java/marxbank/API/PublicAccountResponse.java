package marxbank.API;

import marxbank.model.Account;

public class PublicAccountResponse {
    private Long id;
    private int accountNumber;
    private Long userId;

    protected PublicAccountResponse() {}

    public PublicAccountResponse(Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.userId = account.getUser().getId();
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
}
