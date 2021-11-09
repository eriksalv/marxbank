package marxbank.API;

public class TransferResponse {
    
    private Long id;
    private double amount;

    protected TransferResponse() {}

    public TransferResponse(Long id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
