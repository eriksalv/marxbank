import { AuthState } from "./types";
import { RootState } from "@/store/types";
import { Module } from "vuex";

const state: AuthState = {
  status: "",
  token: null,
  userId: null,
};

export const auth: Module<AuthState, RootState> = {
  state,
};
