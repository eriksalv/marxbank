import { RootState } from "@/store/types";
import { Account, AccountRequest, DepositWithdrawRequest } from "@/types/types";
import axios, { AxiosRequestConfig } from "axios";
import { ActionTree } from "vuex";
import { AccountState } from "./types";

const BASE_URL = "/accounts";

export const actions: ActionTree<AccountState, RootState> = {
  /**
   * Fetches all accounts of the logged in user and saves response data
   */
  async fetchAccounts({ commit }) {
    commit("setAccountStatus", { status: "loading" });
    await axios
      .get(`${BASE_URL}/myAccounts`)
      .then((response) => {
        let accounts: Array<Account> = [];
        response.data.forEach((element: any) => {
          const account: Account = {
            id: element.id,
            userId: element.user,
            name: element.name,
            balance: element.balance,
            type: element.type,
            interest: element.interestRate,
          };
          accounts = [...accounts, account];
        });
        commit("setAccounts", accounts);
        commit("setAccountStatus", { status: "success" });
      })
      .catch((err) => {
        commit("setAccountStatus", { status: "error" });
      });
  },

  /**
   * Fecthes all accounts that a user has had a transaction with,
   * and saves response data
   */
  async fetchAccountsByTransactions({ commit, rootGetters }) {
    commit("setAccountStatus", { status: "loading" });
    await axios
      .get(`${BASE_URL}/transactions`)
      .then((response) => {
        let accounts: Array<Account> = [];
        response.data.forEach((element: any) => {
          const account: Account = {
            id: element.id,
            userId: element.userId,
            name: element.name,
            balance: null,
            type: element.type,
            interest: null,
          };
          accounts = [...accounts, account];
        });
        accounts.forEach((acc) => {
          const allAccounts: Array<Account> = rootGetters.allAccounts;
          if (allAccounts.map((a: Account) => a.id).includes(acc.id)) {
            commit("updateAccount", acc);
          } else {
            commit("addAccount", acc);
          }
        });
        commit("setAccountStatus", { status: "success" });
      })
      .catch((err) => {
        commit("setAccountStatus", { status: "error" });
      });
  },

  /**
   * Fecthes users account by id and saves response data
   * @param id
   */
  async fetchAccountById({ commit, rootGetters }, id: number) {
    commit("setAccountStatus", { status: "loading" });
    await axios
      .get(`${BASE_URL}/myAccounts/${id}`)
      .then((response) => {
        const account: Account = {
          id: response.data.id,
          userId: response.data.user,
          name: response.data.name,
          balance: response.data.balance,
          type: response.data.type,
          interest: response.data.interestRate,
        };
        const allAccounts: Array<Account> = rootGetters.allAccounts;
        if (allAccounts.map((a: Account) => a.id).includes(account.id)) {
          commit("updateAccount", account);
        } else {
          commit("addAccount", account);
        }
        commit("setAccountStatus", { status: "success" });
      })
      .catch((err) => {
        commit("setAccountStatus", { status: "error" });
      });
  },

  /**
   * Fetches a public account (all accounts) by id and saves
   * response data
   * @param id
   */
  async fetchPublicAccountById({ commit, rootGetters }, id: number) {
    commit("setAccountStatus", { status: "loading" });
    await axios
      .get(`${BASE_URL}/${id}`)
      .then((response) => {
        const account: Account = {
          id: response.data.id,
          userId: response.data.userId,
          name: response.data.name,
          balance: null,
          type: response.data.type,
          interest: null,
        };
        const allAccounts: Array<Account> = rootGetters.allAccounts;
        if (allAccounts.map((a: Account) => a.id).includes(account.id)) {
          commit("updateAccount", account);
        } else {
          commit("addAccount", account);
        }
        commit("setAccountStatus", { status: "success" });
      })
      .catch((err) => {
        commit("setAccountStatus", { status: "error" });
      });
  },

  /**
   * Creates a new account from request and saves response data
   * @param request to create account
   */
  async createAccount({ commit }, request: AccountRequest) {
    commit("setAccountStatus", { status: "loading" });
    await axios
      .post(`${BASE_URL}/createAccount`, request)
      .then((response) => {
        const account: Account = {
          id: response.data.id,
          userId: response.data.user,
          name: response.data.name,
          balance: response.data.balance,
          type: response.data.type,
          interest: response.data.interestRate,
        };
        commit("addAccount", account);
        commit("setAccountStatus", { status: "success" });
      })
      .catch((err) => {
        commit("setAccountStatus", {
          status: "error",
          errorMsg: err.response.data.message,
        });
      });
  },

  /**
   * Deposits to the account specified by request and updates account
   * @param request to deposit
   */
  async deposit({ commit }, request: DepositWithdrawRequest) {
    commit("setAccountStatus", { status: "loading" });
    await axios
      .post(`${BASE_URL}/deposit`, request)
      .then((response) => {
        const account: Account = {
          id: response.data.id,
          userId: response.data.user,
          name: response.data.name,
          balance: response.data.balance,
          type: response.data.type,
          interest: response.data.interestRate,
        };
        commit("updateAccount", account);
        commit("setAccountStatus", { status: "success" });
      })
      .catch((err) => {
        commit("setAccountStatus", {
          status: "error",
          errorMsg: err.response.data.message,
        });
      });
  },

  /**
   * Withdraws from the account specified by request and updates account
   * @param request to withdraw
   */
  async withdraw({ commit, rootGetters }, request: DepositWithdrawRequest) {
    commit("setAccountStatus", { status: "loading" });
    await axios
      .post(`${BASE_URL}/withdraw`, request)
      .then((response) => {
        const account: Account = {
          id: response.data.id,
          userId: response.data.user,
          name: response.data.name,
          balance: response.data.balance,
          type: response.data.type,
          interest: response.data.interestRate,
        };
        commit("updateAccount", account);
        commit("setAccountStatus", { status: "success" });
      })
      .catch((err) => {
        commit("setAccountStatus", {
          status: "error",
          errorMsg: err.response.data.message,
        });
      });
  },
};
