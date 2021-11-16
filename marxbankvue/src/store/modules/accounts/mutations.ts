import { Status } from "@/store/types";
import { Account } from "@/types/types";
import { MutationTree } from "vuex";
import { AccountState } from "./types";

export const mutations: MutationTree<AccountState> = {
  setAccounts: (state, accounts: Array<Account>) => {
    state.accounts = accounts;
  },
  addAccount: (state, account: Account) => {
    state.accounts = [...state.accounts, account];
  },
  updateAccount: (state, account: Account) => {
    const i = state.accounts.findIndex((x) => x.id === account.id);
    if (i > -1) state.accounts[i] = account;
  },
  setAccountStatus: (state, status: Status) => {
    state.accountStatus = status;
  },
};
