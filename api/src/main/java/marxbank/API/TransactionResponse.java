package marxbank.API;

import marxbank.model.Transaction;

public class TransactionResponse {
    
    private Long Id;
    private Long fromId;
    private Long recieverId;
    private double amount;
    private String transactionDate;

    public TransactionResponse(){
    }
    
    public TransactionResponse(Transaction transaction){
        this.Id = transaction.getId();
        this.fromId = transaction.getFrom().getId();
        this.recieverId = transaction.getReciever().getId();
        this.amount = transaction.getAmount();
        this.transactionDate = transaction.getDateString();
    }

    public void setId(Long newId) {
        this.Id=newId;
    }
    
    public Long getId() {
        return this.Id;
    }

    public void setFromId(Long fromAccount) {
        this.fromId = fromAccount;
    }

    public Long getFromId(){
        return this.fromId;
    }
    
    public void setRecieverId(Long receivingAccount) {
        this.recieverId = receivingAccount;
    }

    public Long getRecieverId() {
        return this.recieverId;
    }

    public void setAmount(double newAmount ){
        this.amount = newAmount;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setTransactionDate(String newDate) {
        this.transactionDate = newDate;
    }
    public String getTransactionDate() {
        return this.transactionDate;
    }

}
