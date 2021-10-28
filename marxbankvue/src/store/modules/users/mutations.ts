import { MutationTree } from "vuex";
import { User, UserState } from "./types";

export const mutations: MutationTree<UserState> = {
  setUsers: (state, users: Array<User>) => {
    state.users = users;
  },
};