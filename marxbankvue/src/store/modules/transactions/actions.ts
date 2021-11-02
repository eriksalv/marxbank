import { RestService } from "@/service/restService";
import { RootState } from "@/store/types";
import { ActionTree } from "vuex";
import { TransactionState } from "./types";

export const actions: ActionTree<TransactionState, RootState> = {
  async fetchTransactions({ commit }) {
    //man interagerer aldri med andre brukeres transaksjoner,
    //så trenger bare hente sine egne
    const api = new RestService();
    const response = await api.get("transactions");
    console.log(response);
    commit("setTransactions", response);
    return response;
  },
};
