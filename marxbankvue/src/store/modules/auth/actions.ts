import { RootState } from "@/store/types";
import { ActionTree } from "vuex";
import { AuthState } from "./types";
import { LoginRequest, LoginResponse, SignUpRequest } from "@/types/types";
import axios from "axios";

const BASE_URL = "/auth";

export const actions: ActionTree<AuthState, RootState> = {
  /**
   * creates a logout request to api, and logs the user
   * out by setting tokenData to null
   */
  async logout({ commit }) {
    await axios.post(BASE_URL + "/logout").then(() => {
      commit("setStatus", { status: "" });
      commit("setTokenData", {
        userId: null,
        token: null,
        expiresIn: null,
      });
      delete axios.defaults.headers.common["Authorization"];
      localStorage.removeItem("tokenData");
    });
  },
  /**
   * creates a login request to api and stores the returned
   * user
   * @param user login request
   */
  async login({ commit }, user: LoginRequest): Promise<void> {
    commit("setStatus", { status: "loading" });
    await axios
      .post(BASE_URL + "/login", {
        username: user.username,
        password: user.password,
      })
      .then((response) => {
        const tokenData = {
          userId: response.data.userResponse.id,
          token: response.data.token,
          expiresIn: response.data.expiresIn,
        };
        axios.defaults.headers.common["Authorization"] = tokenData.token;
        localStorage.setItem("tokenData", JSON.stringify(tokenData));
        commit("setStatus", { status: "success" });
        commit("setTokenData", tokenData);
        commit("setStatusCode", response.status);
      })
      .catch((err) => {
        if (err.response.status !== undefined) {
          commit("setStatus", {
            status: "error",
            errorMsg: err.response.data.message,
          });
          commit("setStatusCode", err.response.status);
        }
      });
  },
  /**
   * creates a signup request to api and stores the returned
   * user
   * @param user signup request
   */
  async signup({ commit }, user: SignUpRequest) {
    commit("setStatus", { status: "loading" });
    await axios
      .post(BASE_URL + "/signup", {
        username: user.username,
        password: user.password,
        email: user.email,
      })
      .then((response) => {
        const tokenData = {
          userId: response.data.userResponse.id,
          token: response.data.token,
          expiresIn: response.data.expiresIn,
        };
        axios.defaults.headers.common["Authorization"] = tokenData.token;
        localStorage.setItem("tokenData", JSON.stringify(tokenData));
        commit("setStatus", { status: "success" });
        commit("setTokenData", tokenData);
        commit("setStatusCode", response.status);
      })
      .catch((err) => {
        commit("setStatusCode", err.response.status);
        commit("setStatus", {
          status: "error",
          errorMsg: err.response.data.message,
        });
      });
  },

  autoLogin({ commit }) {
    const tokenData = localStorage.getItem("tokenData");
    if (tokenData) {
      commit("setTokenData", JSON.parse(tokenData));
      axios.defaults.headers.common["Authorization"] =
        JSON.parse(tokenData).token;
    }
  },
};
