import { RestService } from "@/service/restService";
import { RootState } from "@/store/types";
import { ActionTree } from "vuex";
import { User, UserState } from "./types";

export const actions: ActionTree<UserState, RootState> = {
  async fetchUsers({ commit }) {
    //TODO: få tak i token fra auth modul når den er implementert
    const api = new RestService();
    const response = await api.get("users");
    console.log(response);
    commit("setUsers", response);
    return response;
  },
};