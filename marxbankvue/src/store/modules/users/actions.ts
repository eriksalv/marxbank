import { RootState } from "@/store/types";
import { ActionTree } from "vuex";
import { User, UserState } from "./types";
import axios from "axios";
import { EditUserRequest } from "../../../types/types";

const BASE_URL = "/users";

export const actions: ActionTree<UserState, RootState> = {
  async fetchUserById({ commit, rootGetters }, id: number) {
    commit("setUserStatus", { status: "loading" });
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
        commit("setUserStatus", { status: "success" });
      })
      .catch((err) => {
        commit("setUserStatus", { status: "error" });
      });
  },

  async editUser(
    { commit, rootGetters },
    body: {
      id: number;
      request: EditUserRequest;
    }
  ) {
    commit("setUserStatus", { status: "loading" });
    await axios
      .post(`${BASE_URL}/${body.id}/edit`, body.request, {
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
        commit("setUserStatus", { status: "success" });
      })
      .catch((err) => {
        commit("setUserStatus", {
          status: "error",
          errorMsg: err.response.data.message,
        });
      });
  },
};
