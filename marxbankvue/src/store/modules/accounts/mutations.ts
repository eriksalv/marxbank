import { MutationTree } from "vuex";
import { Account, AccountState } from "./types";

export const mutations: MutationTree<AccountState> = {
  setAccounts: (state, accounts: Array<Account>) => {
    state.accounts = accounts;
  },
};
