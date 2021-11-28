import { RootState } from "@/store/types";
import { Module } from "vuex";
import { TransactionState } from "./types";
import { getters } from "./getters";
import { mutations } from "./mutations";
import { actions } from "./actions";

const state: TransactionState = {
  transactionStatus: "",
  transactions: [],
};

export const transactions: Module<TransactionState, RootState> = {
  state,
  getters,
  mutations,
  actions,
};
