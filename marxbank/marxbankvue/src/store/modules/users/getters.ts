import { RootState } from "@/store/types";
import { GetterTree } from "vuex";
import { User, UserState } from "./types";

export const getters: GetterTree<UserState, RootState> = {
  getLoggedInUser: (state) => state.loggedInUser,
  userStatus: (state) => state.userStatus,
};
