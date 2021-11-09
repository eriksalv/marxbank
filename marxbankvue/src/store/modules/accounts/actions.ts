import { RootState } from "@/store/types";
import { Account, AccountRequest, DepositWithdrawRequest } from "@/types/types";
import axios from "axios";
import { ActionTree } from "vuex";
import { auth } from "../auth";
import { AccountState } from "./types";

const BASE_URL = "/accounts";

export const actions: ActionTree<AccountState, RootState> = {
  async fetchAccounts({ commit, rootGetters }) {
    //TODO: få tak i token fra auth modul når den er implementert
    commit("setAccountStatus", "loading");
    await axios
      .get(`${BASE_URL}/myAccounts`, rootGetters.getToken)
      .then((response) => {
        console.log("yeet");
        let accounts: Array<Account> = [];
        response.data.forEach((element: any) => {
          console.log(element);
          const account: Account = {
            id: element.id,
            userId: element.user,
            name: element.name,
            accNumber: element.accountNumber,
            balance: element.balance,
            type: element.type,
            interest: element.interestRate
          }
          accounts = [...accounts, account];
        });
        commit("setAccounts", accounts);
        commit("setAccountStatus", "success");
      })
      .catch((err) => {
        commit("setAccountStatus", "error");
      })
  },

  async createAccount({commit, rootGetters}, request: AccountRequest) {
    commit("setAccountStatus", "loading");
    await axios
      .post(`${BASE_URL}/createAccount`, request, {
        headers: {
          Authorization: rootGetters.getToken,
        },
      })
      .then((response) => {
        const account: Account = {
          id: response.data.id,
          userId: response.data.userId,
          name: response.data.name,
          accNumber: response.data.accountNumber,
          balance: response.data.balance,
          type: response.data.type,
          interest: response.data.interestRate
        }
        commit("addAccount", account);
        commit("setAccountStatus", "success");
      })
      .catch((err) => {
        commit("setAccountStatus", "error");
      })
  },

  async deposit({commit, rootGetters}, request: DepositWithdrawRequest) {
    commit("setAccountStatus", "loading");
    await axios
      .post(`${BASE_URL}/deposit`, request, {
        headers: {
          Authorization: rootGetters.getToken,
        },
      })
      .then((response) => {
        const account: Account = {
          id: response.data.id,
          userId: response.data.userId,
          name: response.data.name,
          accNumber: response.data.accountNumber,
          balance: response.data.balance,
          type: response.data.type,
          interest: response.data.interestRate
        }
        commit("updateAccount", account);
        commit("setAccountStatus", "success");
      })
      .catch((err) => {
        commit("setAccountStatus", "error");
      })
  },

  async withdraw({commit, rootGetters}, request: DepositWithdrawRequest) {
    commit("setAccountStatus", "loading");
    await axios
      .post(`${BASE_URL}/withdraw`, request, {
        headers: {
          Authorization: rootGetters.getToken,
        },
      })
      .then((response) => {
        const account: Account = {
          id: response.data.id,
          userId: response.data.userId,
          name: response.data.name,
          accNumber: response.data.accountNumber,
          balance: response.data.balance,
          type: response.data.type,
          interest: response.data.interestRate
        }
        commit("updateAccount", account);
        commit("setAccountStatus", "success");
      })
      .catch((err) => {
        commit("setAccountStatus", "error");
      })
  }
};
