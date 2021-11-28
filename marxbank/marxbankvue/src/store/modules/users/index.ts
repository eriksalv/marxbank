import { RootState } from "@/store/types";
import { Module } from "vuex";
import { UserState } from "./types";
import { getters } from "./getters";
import { mutations } from "./mutations";
import { actions } from "./actions";

const state: UserState = {
  userStatus: "",
  loggedInUser: {
    id: 0,
    username: "",
    email: "",
  },
};

export const users: Module<UserState, RootState> = {
  state,
  getters,
  mutations,
  actions,
};
