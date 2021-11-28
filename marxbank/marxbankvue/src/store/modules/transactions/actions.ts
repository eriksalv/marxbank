import { RootState } from "@/store/types";
import { TransactionRequest } from "@/types/types";
import axios from "axios";
import { ActionTree } from "vuex";
import { Transaction, TransactionState } from "./types";

const BASE_URL = "/transactions";

export const actions: ActionTree<TransactionState, RootState> = {
  /**
   * fetches all transactions for the logged in user'
   * and sets the returned transactions using the
   * setTransactions-mutation.
   */
  async fetchTransactions({ commit }) {
    commit("setTransactionStatus", { status: "loading" });
    await axios
      .get(BASE_URL + "/myTransactions")
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
        commit("setTransactionStatus", { status: "success" });
      })
      .catch((err) => {
        commit("setTransactionStatus", { status: "error" });
      });
  },
  /**
   * sends a transaction request and recieves the created
   * transaction as a response, which is then added to state
   * using the addTransaction-mutation assuming nothing went wrong
   * @param transactionRequest request to be sent
   */
  async createTransaction({ commit }, transactionRequest: TransactionRequest) {
    commit("setTransactionStatus", { status: "loading" });
    await axios
      .post(BASE_URL + "/transfer", transactionRequest)
      .then((response) => {
        const transaction: Transaction = {
          id: response.data.id,
          from: response.data.fromId,
          to: response.data.recieverId,
          amount: response.data.amount,
          date: response.data.transactionDate,
        };
        commit("addTransaction", transaction);
        commit("setTransactionStatus", { status: "success" });
      })
      .catch((err) => {
        commit("setTransactionStatus", {
          status: "error",
          errorMsg: err.response.data.message,
        });
      });
  },
  /**
   * Fetches all transactions for a single account, and sets these
   * transactions to state. Useful when fetching all transactions
   * for a user isn't necessary
   * @param id id of account
   */
  async fetchTransactionsByAccount({ commit }, id: number) {
    commit("setTransactionStatus", { status: "loading" });
    await axios
      .get(`${BASE_URL}/myTransactions/${id}`)
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
        commit("setTransactionStatus", { status: "success" });
      })
      .catch((err) => {
        commit("setTransactionStatus", { status: "error" });
      });
  },
};
