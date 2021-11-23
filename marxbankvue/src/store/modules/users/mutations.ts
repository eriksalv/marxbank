import { Status } from "@/store/types";
import { MutationTree } from "vuex";
import { User, UserState } from "./types";

export const mutations: MutationTree<UserState> = {
  setLoggedInUser: (state, user: User) => {
    state.loggedInUser = user;
  },
  setUserStatus: (state, payload: { status: Status; errorMsg?: String }) => {
    state.userStatus = payload.status;
    if (typeof payload.errorMsg !== "undefined") {
      throw new Error(payload.errorMsg.toString());
    }
  },
};
