import { RestService } from "@/service/restService";
import { RootState } from "@/store/types";
import { ActionTree } from "vuex";
import { Account, AccountState } from "./types";

export const actions: ActionTree<AccountState, RootState> = {
  async fetchAccounts({ commit }) {
    //TODO: få tak i token fra auth modul når den er implementert
    const api = new RestService();
    const response = await api.get("accounts");
    console.log(response);
    commit("setAccounts", response);
    return response;
  },
};
