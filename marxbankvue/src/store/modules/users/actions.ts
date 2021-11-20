import { RootState } from "@/store/types";
import { ActionTree } from "vuex";
import { User, UserState } from "./types";
import axios from "axios";

const BASE_URL = "/users";

export const actions: ActionTree<UserState, RootState> = {
  async fetchUserById({ commit, rootGetters }, id: number) {
    commit("setUserStatus", "loading");
    await axios
      .get(`${BASE_URL}/${id}`, {
        headers: {
          Authorization: rootGetters.getToken,
        },
      })
      .then((response) => {
        const user: User = {
          id: response.data.id,
          username: response.data.username,
          email: response.data.email,
        };
        commit("setLoggedInUser", user);
        commit("setUserStatus", "success");
      })
      .catch((err) => {
        commit("setUserStatus", "error");
      });
  },
};
