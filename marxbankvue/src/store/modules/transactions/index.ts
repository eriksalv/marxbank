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
      from: 1811,
      to: 2020,
      amount: 200,
    },
    {
      date: "20-10-2021",
      from: 2020,
      to: 1512,
      amount: 50,
    },
    {
      date: "11-10-2021",
      from: 2020,
      to: 1288,
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
