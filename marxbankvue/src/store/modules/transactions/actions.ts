import { RootState } from "@/store/types";
import { TransactionRequest } from "@/types/types";
import axios from "axios";
import { ActionTree } from "vuex";
import { Transaction, TransactionState } from "./types";

const BASE_URL = "/transactions";

export const actions: ActionTree<TransactionState, RootState> = {
  async fetchTransactions({ commit, rootGetters }) {
    commit("setTransactionStatus", "loading");
    await axios
      .get(BASE_URL + "/myTransactions", rootGetters.getToken)
      .then((response) => {
        let transactions: Array<Transaction> = [];
        response.data.forEach((element: any) => {
          const newTransaction: Transaction = {
            id: element.Id,
            from: element.from.id,
            to: element.to.id,
            amount: element.amount,
            date: element.transactionDateString,
          };
          transactions = [...transactions, newTransaction];
        });
        commit("setTransactions", transactions);
        commit("setTransactionStatus", "success");
      })
      .catch((err) => {
        commit("setTransactionStatus", "error");
      });
  },
  async createTransaction(
    { commit, rootGetters },
    transferRequest: TransactionRequest
  ) {
    commit("setTransactionStatus", "loading");
    await axios
      .post("/accounts/transfer", {
        token: rootGetters.getToken,
        request: transferRequest,
      })
      .then((response) => {
        let transactions: Array<Transaction> = [];
        response.data.forEach((element: any) => {
          const newTransaction: Transaction = {
            id: element.Id,
            from: element.from.id,
            to: element.to.id,
            amount: element.amount,
            date: element.transactionDateString,
          };
          transactions = [...transactions, newTransaction];
        });
        commit("setTransactions", transactions);
        commit("setTransactionStatus", "success");
      })
      .catch((err) => {
        commit("setTransactionStatus", "error");
      });
  },
};
