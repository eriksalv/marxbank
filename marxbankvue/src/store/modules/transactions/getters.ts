import { RootState } from "@/store/types";
import { GetterTree } from "vuex";
import { Transaction, TransactionState } from "./types";

export const getters: GetterTree<TransactionState, RootState> = {
  allTransactions: (state): Array<Transaction> => state.transactions,
  /**
   * Filters transactions in state by a specific account
   * @param state state of the all transactions
   * @param accountId id of the account to filter by
   * @returns filtered array of transactions
   */
  filterTransactionsByAccount: (state) => (
    accountId: number
  ): Array<Transaction> => {
    return state.transactions.filter(
      (t) => t.from === accountId || t.to === accountId
    );
  },
};
