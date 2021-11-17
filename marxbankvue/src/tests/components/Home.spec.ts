import { AccountState } from "../../store/modules/accounts/types";
import { TransactionState } from "../../store/modules/transactions/types";
import { mount } from "@vue/test-utils";
import { createStore, Store } from "vuex";
import AccountList from "../../components/AccountList.vue";
import Account from "../../components/Account.vue";
import AccountInfo from "../../views/AccountInfo.vue";
import { Plugin } from "@vue/runtime-core";
import flushPromises from "flush-promises";

import Home from "../../views/Home.vue";
import MyFavorite from "../../components/MyFavorite.vue";
import MyFavorites from "../../components/MyFavorites.vue";



describe("Home", () => {
    const initAccountState: AccountState = {
      accountStatus: "",
      accounts: [
        {
          id: 1,
          userId: 1,
          accNumber: 1,
          balance: 200,
          type: "Sparekonto",
          name: "test1",
          interest: 3.0,
        },
        {
          id: 2,
          userId: 1,
          accNumber: 2,
          balance: 300,
          type: "Sparekonto",
          name: "test2",
          interest: 3.0,
        },
      ],
    };
  
    const initTransactionState: TransactionState = {
      transactionStatus: "",
      transactions: [
        {
          id: 1,
          from: 1,
          to: 2,
          amount: 200,
          date: "13-11-2021",
        },
      ],
    };

    let mockAllTransactions: jest.Mock<any, any>;
    let mockAllAccounts: jest.Mock<any, any>;
    let store: Store<any> | Plugin | [Plugin, ...any[]];
  
    beforeEach(() => {
      mockAllTransactions = jest.fn().mockReturnValue(initTransactionState.transactions);
      mockAllAccounts = jest.fn().mockReturnValue(initAccountState.accounts);

      store = createStore({
        state: {
          initTransactionState,
          initAccountState,
        },
        getters: {
          allTransactions: mockAllTransactions,
          allAccounts: mockAllAccounts
        },
      });
    });
  
    test("test initial state", () => {
      const wrapper = mount(Home, {
        global: { plugins: [store] },
      });
  
      expect(wrapper.html()).toContain("13-11-2021");
      expect(wrapper.html()).toContain("test1");
      expect(wrapper.html()).toContain("test2");
      expect(wrapper.html()).toContain("Totalbeløp på konto:");

      expect(mockAllTransactions).toHaveBeenCalled();
      expect(mockAllAccounts).toHaveBeenCalled();
    });

    test("test go to savings calculator", async () => {
        const wrapper = mount(Home, {
          global: { plugins: [store] },
        });
  
        await wrapper.find("#goToCalc").trigger("click");

        expect(wrapper.html()).toContain("Engangsbeløp");
        
      });
  
    
  
   
  });
  