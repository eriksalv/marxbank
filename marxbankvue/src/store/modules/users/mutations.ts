import { Status } from "@/store/types";
import { MutationTree } from "vuex";
import { User, UserState } from "./types";

export const mutations: MutationTree<UserState> = {
  setLoggedInUser: (state, user: User) => {
    state.loggedInUser = user;
  },
  setUserStatus: (state, status: Status) => {
    state.userStatus = status;
  },
};
