import { createStore, StoreOptions } from "vuex";
import { accounts } from "./modules/accounts/index";
import { transactions } from "./modules/transactions/index";
import { users } from "./modules/users";
import { auth } from "./modules/auth";
import { RootState } from "./types";

const store: StoreOptions<RootState> = {
  state: {
    message: "",
  },
  modules: {
    accounts,
    transactions,
    users,
    auth,
  },
};

//Create store
export default createStore<RootState>(store);
