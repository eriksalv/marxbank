import { createStore, StoreOptions } from "vuex";
import { accounts } from "./modules/accounts/index";
import { transactions } from "./modules/transactions/index";
import { users } from "./modules/users";
import { RootState } from "./types";

const store: StoreOptions<RootState> = {
  state: {
    message: "hello",
  },
  modules: {
    accounts,
    transactions,
    users,
  },
};

//Create store
export default createStore<RootState>(store);
