package marxbank.API;

public class TransferRequest {

    private Long from;
    private Long to;
    private double amount;

    protected TransferRequest() {}

    public TransferRequest(double amount, Long from, Long to) {
        this.amount = amount;
        this.from = from;
        this.to = to;
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
