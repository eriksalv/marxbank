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
            id: element.id,
            from: element.fromId,
            to: element.recieverId,
            amount: element.amount,
            date: element.transactionDate,
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
    transactionRequest: TransactionRequest
  ) {
    commit("setTransactionStatus", "loading");
    await axios
      .post(BASE_URL + "/transfer", transactionRequest, {
        headers: {
          Authorization: rootGetters.getToken,
        },
      })
      .then((response) => {
        const transaction: Transaction = {
          id: response.data.id,
          from: response.data.fromId,
          to: response.data.recieverId,
          amount: response.data.amount,
          date: response.data.transactionDate,
        };
        commit("addTransaction", transaction);
        commit("setTransactionStatus", "success");
      })
      .catch((err) => {
        commit("setTransactionStatus", "error");
      });
  },
};
