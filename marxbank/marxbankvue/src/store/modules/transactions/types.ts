import { Status } from "@/store/types";

export interface Transaction {
  readonly id: number;
  from: number;
  to: number;
  amount: number;
  date: string;
}

export interface TransactionState {
  transactionStatus: Status;
  transactions: Array<Transaction>;
}
