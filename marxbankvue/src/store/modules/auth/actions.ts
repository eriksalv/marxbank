import { RootState } from "@/store/types";
import { ActionTree } from "vuex";
import { AuthState } from "./types";
import { LoginRequest, LoginResponse, SignUpRequest } from "@/types/types";
import axios from "axios";

const BASE_URL = "/auth";
let timer: number;

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
      if (timer) {
        clearTimeout(timer);
      }
    });
  },
  /**
   * creates a login request to api and stores the returned
   * user
   * @param user login request
   */
  async login({ commit, dispatch }, user: LoginRequest): Promise<void> {
    commit("setStatus", { status: "loading" });
    await axios
      .post(BASE_URL + "/login", {
        username: user.username,
        password: user.password,
      })
      .then((response) => {
        const expirationTime = +response.data.expiresIn;
        timer = setTimeout(() => {
          dispatch("autoLogout");
        }, expirationTime);

        const tokenData = {
          userId: response.data.userResponse.id,
          token: response.data.token,
          expiresIn: expirationTime,
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
  async signup({ commit, dispatch }, user: SignUpRequest) {
    commit("setStatus", { status: "loading" });
    await axios
      .post(BASE_URL + "/signup", {
        username: user.username,
        password: user.password,
        email: user.email,
      })
      .then((response) => {
        const expirationTime = +response.data.expiresIn;
        timer = setTimeout(() => {
          dispatch("autoLogout");
        }, expirationTime);

        const tokenData = {
          userId: response.data.userResponse.id,
          token: response.data.token,
          expiresIn: expirationTime,
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

  autoLogin({ commit, dispatch }) {
    const tokenDataString = localStorage.getItem("tokenData");
    if (tokenDataString) {
      const tokenData = JSON.parse(tokenDataString);
      const expirationTime = tokenData.expiresIn - new Date().getTime();

      if (expirationTime < 60000) {
        //refresh token if expirationTime is less than 60 seconds
        commit("setExpiresIn", tokenData.expiresIn);
        timer = setTimeout(() => {
          dispatch("autoLogout");
        }, tokenData.expiresIn);
      } else {
        timer = setTimeout(() => {
          dispatch("autoLogout");
        }, expirationTime);
      }

      commit("setTokenData", tokenData);
      axios.defaults.headers.common["Authorization"] = tokenData.token;
    }
  },

  autoLogout({ commit, dispatch }) {
    dispatch("logout");
    commit("setAutoLogout");
  },
};
