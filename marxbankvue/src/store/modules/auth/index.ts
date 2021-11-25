import { AuthState } from "./types";
import { RootState } from "@/store/types";
import { Module } from "vuex";
import { getters } from "./getters";
import { mutations } from "./mutations";
import { actions } from "./actions";

const state: AuthState = {
  status: "",
  tokenData: {
    userId: null,
    token: null,
    expiresIn: null,
  },
  statusCode: 0,
};

export const auth: Module<AuthState, RootState> = {
  state,
  getters,
  mutations,
  actions,
};
