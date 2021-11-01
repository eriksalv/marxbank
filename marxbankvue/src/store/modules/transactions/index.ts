import { RootState } from "@/store/types";
import { Module } from "vuex";
import { TransactionState } from "./types";
import { getters } from "./getters";
import { mutations } from "./mutations";
import { actions } from "./actions";

const state: TransactionState = {
  transactions: [
    {
      date: "22-10-2021",
      from: 1,
      to: 2,
      amount: 200,
    },
    {
      date: "20-10-2021",
      from: 3,
      to: 1,
      amount: 50,
    },
    {
      date: "11-10-2021",
      from: 2,
      to: 3,
      amount: 149,
    },
  ],
};

export const transactions: Module<TransactionState, RootState> = {
  state,
  getters,
  mutations,
  actions,
};
