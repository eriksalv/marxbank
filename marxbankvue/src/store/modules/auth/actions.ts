import { RootState } from "@/store/types";
import { RestService } from "@/service/restService";
import { ActionTree } from "vuex";
import { AuthState } from "./types";
import { LoginRequest, LoginResponse, SignUpRequest } from "@/types/types";
import axios from "axios";

const api = new RestService();

const BASE_URL = "/auth";

export const actions: ActionTree<AuthState, RootState> = {
  /**
   * creates a logout request to api, and logs the user
   * out by setting token to null
   * @param token before logout
   */
  async logout({ commit }, token: object | null) {
    console.log(token);
    await axios.post(BASE_URL + "/logout", token).then(() => {
      commit("setStatus", "");
      commit("setToken", null);
      delete axios.defaults.headers.common["Authorization"];
    });
  },
  /**
   * creates a login request to api and stores the returned
   * user
   * @param user login request
   */
  async login({ commit }, user: LoginRequest) {
    commit("setStatus", "loading");
    await axios
      .post(BASE_URL + "/login", {
        username: user.username,
        password: user.password,
      })
      .then((response) => {
        const userId = response.data.userResponse.id;
        const token = response.data.token;
        axios.defaults.headers.common["Authorization"] = token;
        commit("setUserId", userId);
        commit("setStatus", "success");
        commit("setToken", token);
      })
      .catch((err) => {
        commit("setStatus", "error", err);
      });
  },
  /**
   * creates a signup request to api and stores the returned
   * user
   * @param user signup request
   */
  async signup({ commit }, user: SignUpRequest) {
    commit("setStatus", "loading");
    await axios
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
        commit("setStatus", "success");
        commit("setToken", token);
      })
      .catch((err) => {
        commit("setStatus", "error", err);
      });
  },
};
