import { Status } from "@/store/types";
import { MutationTree } from "vuex";
import { AuthState, TokenData } from "./types";

export const mutations: MutationTree<AuthState> = {
  /**
   * Sets the token to a string, or removes it
   * by setting it to null
   * @param state authState
   * @param token
   */
  setToken: (state, token: string | null) => {
    state.tokenData.token = token;
  },
  setExpiresIn: (state, expiresIn: number | null) => {
    state.tokenData.expiresIn = expiresIn;
    state.autoLogout = false;
  },
  setStatus: (state, payload: { status: Status; errorMsg?: String }) => {
    state.status = payload.status;
    if (typeof payload.errorMsg !== "undefined") {
      throw new Error(payload.errorMsg.toString());
    }
  },
  setUserId: (state, userId: number) => {
    state.tokenData.userId = userId;
  },
  setStatusCode: (state, statusCode: number) => {
    state.statusCode = statusCode;
  },
  setTokenData: (state, tokenData: TokenData) => {
    state.tokenData = tokenData;
    state.autoLogout = false;
  },
  setAutoLogout: (state) => {
    state.autoLogout = true;
  },
};
