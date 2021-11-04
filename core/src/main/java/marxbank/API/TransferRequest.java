package marxbank.API;

public class TransferRequest {

    private Long from;
    private Long to;
    private boolean withdraw;
    private double amount;

    protected TransferRequest() {}

    public TransferRequest(boolean withdraw, double amount, Long from, Long to) {
        this.withdraw = withdraw;
        this.amount = amount;
        this.from = from;
        this.to = to;
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
        this.amount = a;
    }

    public Long getTo() {
        return this.to;
    }

    public void setTo(Long id) {
        this.to = id;
    }

    public Long getFrom() {
        return this.from;
    }

    public void setFrom(Long id) {
        this.from = id;
    }
    
}
