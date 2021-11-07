import { RootState } from "@/store/types";
import { Module } from "vuex";
import { AccountState } from "./types";
import { getters } from "./getters";
import { mutations } from "./mutations";
import { actions } from "./actions";

const state: AccountState = {
  accounts: [
    {
      id: 1,
      userId: 2,
      name: "test",
      accNumber: 200,
      balance: 200,
      interest: 3.0,
      type: "Sparekonto",
    },
    {
      id: 2,
      userId: 1,
      name: "test2",
      accNumber: 201,
      balance: 200,
      interest: 3.0,
      type: "Sparekonto",
    },
    {
      id: 3,
      userId: 1,
      name: "test3",
      accNumber: 203,
      balance: 200,
      interest: 3.0,
      type: "Sparekonto",
    },
  ],
};

export const accounts: Module<AccountState, RootState> = {
  state,
  getters,
  mutations,
  actions,
};
