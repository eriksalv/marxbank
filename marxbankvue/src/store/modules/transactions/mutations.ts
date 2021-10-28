import { MutationTree } from "vuex";
import { Transaction, TransactionState } from "./types";

export const mutations: MutationTree<TransactionState> = {
  setTransactions: (state, transactions: Array<Transaction>) => {
    state.transactions = transactions;
  },
};
