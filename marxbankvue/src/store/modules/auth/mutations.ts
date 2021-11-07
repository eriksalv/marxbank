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
  setStatus: (state, status: Status) => {
    state.status = status;
  },
  setUserId: (state, userId: number) => {
    state.userId = userId;
  },
};
