import { Status } from "@/store/types";
import { MutationTree } from "vuex";
import { AuthState } from "./types";

export const mutations: MutationTree<AuthState> = {
  /**
   * Sets the token to a string, or removes it
   * by setting it to null
   * @param state authState
   * @param token
   */
  setToken: (state, token: string | null) => {
    state.token = token;
  },
  setStatus: (state, payload: { status: Status; errorMsg?: String }) => {
    state.status = payload.status;
    if (typeof payload.errorMsg !== "undefined") {
      throw new Error(payload.errorMsg.toString());
    }
  },
  setUserId: (state, userId: number) => {
    state.userId = userId;
  },
  setStatusCode: (state, statusCode: number) => {
    state.statusCode = statusCode;
  },
};
