import { RootState } from "@/store/types";
import { GetterTree } from "vuex";
import { User, UserState } from "./types";

export const getters: GetterTree<UserState, RootState> = {
  currentUser: (state) => (token: string): User | undefined => state.users.find(user => user.token === token),
};