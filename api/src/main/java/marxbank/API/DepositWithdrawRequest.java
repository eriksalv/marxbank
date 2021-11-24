package marxbank.API;

public class DepositWithdrawRequest {
  private double amount;
  private Long accountId;

  protected DepositWithdrawRequest() {}

  public DepositWithdrawRequest(double amount, Long accountId) {
    this.amount = amount;
    this.accountId = accountId;
  }

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
