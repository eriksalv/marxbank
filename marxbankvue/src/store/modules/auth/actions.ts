import { RootState } from "@/store/types";
import { ActionTree } from "vuex";
import { AuthState } from "./types";
import { LoginRequest, LoginResponse, SignUpRequest } from "@/types/types";
import axios from "axios";

const BASE_URL = "/auth";

export const actions: ActionTree<AuthState, RootState> = {
  /**
   * creates a logout request to api, and logs the user
   * out by setting token to null
   * @param token before logout
   */
  async logout({ commit }, token: object | null) {
    await axios.post(BASE_URL + "/logout", token).then(() => {
      commit("setStatus", { status: "" });
      commit("setToken", null);
      delete axios.defaults.headers.common["Authorization"];
    });
  },
  /**
   * creates a login request to api and stores the returned
   * user
   * @param user login request
   */
  async login({ commit }, user: LoginRequest): Promise<void> {
    commit("setStatus", { status: "loading" });
    return new Promise<void>((resolve, reject) => {
      axios
        .post(BASE_URL + "/login", {
          username: user.username,
          password: user.password,
        })
        .then((response) => {
          const userId: Number = response.data.userResponse.id;
          const token: string = response.data.token;
          axios.defaults.headers.common["Authorization"] = token;
          commit("setUserId", userId);
          commit("setStatus", { status: "success" });
          commit("setToken", token);
          commit("setStatusCode", response.status);
          resolve();
        })
        .catch((err) => {
          if (err.response.status !== undefined) {
            commit("setStatus", {
              status: "error",
              errorMsg: err.response.data.message,
            });
            commit("setStatusCode", err.response.status);
          }
          reject();
        });
    });
  },
  /**
   * creates a signup request to api and stores the returned
   * user
   * @param user signup request
   */
  async signup({ commit }, user: SignUpRequest): Promise<void> {
    commit("setStatus", { status: "loading" });
    return new Promise<void>((resolve, reject) => {
      axios
        .post(BASE_URL + "/signup", {
          username: user.username,
          password: user.password,
          email: user.email,
        })
        .then((response) => {
          const userId = response.data.userResponse.id;
          const token = response.data.token;
          axios.defaults.headers.common["Authorization"] = token;
          commit("setUserId", userId);
          commit("setStatus", { status: "success" });
          commit("setToken", token);
          commit("setStatusCode", response.status);
          resolve();
        })
        .catch((err) => {
          commit("setStatusCode", err.response.status);
          commit("setStatus", {
            status: "error",
            errorMsg: err.response.data.message,
          });
          reject();
        });
    });
  },
};
