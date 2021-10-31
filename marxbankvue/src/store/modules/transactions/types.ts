export interface Transaction {
  readonly date: string;
  from: number;
  to: number;
  amount: number;
}

export interface TransactionState {
  transactions: Array<Transaction>;
}
