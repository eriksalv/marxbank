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
    return !!state.tokenData.token;
  },
  expiresIn: (state): number | null => {
    return state.tokenData.expiresIn;
  },
  getToken: (state): string | null => {
    return state.tokenData.token;
  },
  authStatus: (state): string => {
    return state.status;
  },
  getUserId: (state): number | null => {
    return state.tokenData.userId;
  },
  getStatusCode: (state): number => {
    return state.statusCode;
  },
};
