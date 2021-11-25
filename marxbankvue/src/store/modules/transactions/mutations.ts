import { Status } from "@/store/types";
import { MutationTree } from "vuex";
import { Transaction, TransactionState } from "./types";

export const mutations: MutationTree<TransactionState> = {
  setTransactions: (state, transactions: Array<Transaction>) => {
    state.transactions = transactions;
  },
  addTransaction: (state, transaction: Transaction) => {
    state.transactions = [...state.transactions, transaction];
  },
  setTransactionStatus: (
    state,
    payload: { status: Status; errorMsg?: String }
  ) => {
    state.transactionStatus = payload.status;
    if (typeof payload.errorMsg !== "undefined") {
      throw new Error(payload.errorMsg.toString());
    }
  },
};
