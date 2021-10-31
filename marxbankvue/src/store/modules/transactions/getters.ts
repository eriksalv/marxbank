import { RootState } from "@/store/types";
import { GetterTree } from "vuex";
import { TransactionState } from "./types";

export const getters: GetterTree<TransactionState, RootState> = {
  allTransactions: (state) => state.transactions,
};
