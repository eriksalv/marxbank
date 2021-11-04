package marxbank.API;

public class AccountTransferRequest {

    private boolean withdraw;
    private double amount;

    protected AccountTransferRequest() {}

    public AccountTransferRequest(boolean withdraw, double amount) {
        this.withdraw = withdraw;
        this.amount = amount;
    }

    public boolean getWithdraw() {
        return this.withdraw;
    }

    public void setWithdraw(boolean w) {
        this.withdraw = w;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double a) {
        this.amount = amount;
    }
    
}
