import { RootState } from "@/store/types";
import { GetterTree } from "vuex";
import { AuthState } from "./types";

export const getters: GetterTree<AuthState, RootState> = {
  /**
   * Checks if user is logged in, i.e that token is set
   * @param state authState
   * @returns true if token is not equal to null
   */
  isLoggedIn: (state): boolean => {
    return !!state.token;
  },
  getToken: (state): string | null => {
    return state.token;
  },
  authStatus: (state): string => {
    return state.status;
  },
  getUserId: (state): number | null => {
    return state.userId;
  },
};
