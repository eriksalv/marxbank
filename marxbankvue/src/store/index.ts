import { createStore } from 'vuex'
import accounts from "./modules/accounts";
import transactions from "./modules/transactions";
import users from "./modules/users";

//Create store
export default createStore({
    modules: {
        accounts, 
        transactions,
        users
    }
})
