package marxbank.API;

/**
 * Klassen tar inn den nøvendige informasjonen fra en bruker for å gjennomføre et innskudd eller uttak av penger.
 */
public class DepositWithdrawRequest {
    private double amount;
    private Long accountId;

/**
 * Tom konstruktør.
 */
    protected DepositWithdrawRequest() {}

/**
 * En konstruktør som tar inn beløpet og kontoen, og setter tilstanden til this lik dette.
 * @param amount - beløpet
 * @param accountId - kontoen brukeren ønsker å ta ut fra eller sette penger inn på
 */
    public DepositWithdrawRequest(double amount, Long accountId) {
        this.amount = amount;
        this.accountId = accountId;
    }

/**
 * Getters og setters.
 */
    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long id) {
        this.accountId = id;
    }
}
