import { RootState } from "@/store/types";
import { Module } from "vuex";
import { AccountState } from "./types";
import { getters } from "./getters";
import { mutations } from "./mutations";
import { actions } from "./actions";

const state: AccountState = {
  accountStatus: "",
  accounts: [],
};

export const accounts: Module<AccountState, RootState> = {
  state,
  getters,
  mutations,
  actions,
};
