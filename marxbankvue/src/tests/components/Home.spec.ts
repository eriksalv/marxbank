import { AccountState } from "../../store/modules/accounts/types";
import { TransactionState } from "../../store/modules/transactions/types";
import { mount } from "@vue/test-utils";
import { createStore, Store } from "vuex";
import { Plugin } from "@vue/runtime-core";

import Home from "../../views/Home.vue";

describe("Home", () => {
  const initAccountState: AccountState = {
    accountStatus: "",
    accounts: [
      {
        id: 1,
        userId: 1,
        balance: 200,
        type: "Sparekonto",
        name: "test1",
        interest: 3.0,
      },
      {
        id: 2,
        userId: 1,
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
        amount: 99,
        date: "13-11-2021",
      },
    ],
  };

  let mockFilterAccountsByUserId: jest.Mock<any, any>;
  let mockAllTransactions: jest.Mock<any, any>;
  let mockGetUserId: jest.Mock<any, any>;
  let mockFetchAccounts: jest.Mock<any, any>;
  let mockFetchAccountsByTransactions: jest.Mock<any, any>;
  let mockFetchTransactions: jest.Mock<any, any>;
  let store: Store<any> | Plugin | [Plugin, ...any[]];

  beforeEach(() => {
    mockAllTransactions = jest
      .fn()
      .mockReturnValue(initTransactionState.transactions);
    mockFilterAccountsByUserId = jest
      .fn()
      .mockReturnValue(initAccountState.accounts);
    mockGetUserId = jest.fn();
    mockFetchAccounts = jest.fn();
    mockFetchAccountsByTransactions = jest.fn();
    mockFetchTransactions = jest.fn();

    store = createStore({
      state: {
        initTransactionState,
        initAccountState,
      },
      getters: {
        allTransactions: mockAllTransactions,
        filterAccountsByUserId: () => mockFilterAccountsByUserId,
        getUserId: mockGetUserId,
      },
      actions: {
        fetchAccounts: mockFetchAccounts,
        fetchAccountsByTransactions: mockFetchAccountsByTransactions,
        fetchTransactions: mockFetchTransactions,
      },
    });
  });

  test("test initial state", () => {
    const wrapper = mount(Home, {
      global: { plugins: [store] },
    });

    expect(wrapper.html()).toContain("kr 99");
    expect(wrapper.html()).toContain("13-11-2021");
    expect(wrapper.html()).toContain("test1");
    expect(wrapper.html()).toContain("test2");
    expect(wrapper.html()).toContain("Totalbeløp på konto:");

    expect(mockAllTransactions).toHaveBeenCalled();
    expect(mockFilterAccountsByUserId).toHaveBeenCalled();
    expect(mockGetUserId).toHaveBeenCalled();
  });

  test("test go to savings calculator", async () => {
    const mockRouter = {
      push: jest.fn(),
    };

    const wrapper = mount(Home, {
      global: {
        plugins: [store],
        mocks: {
          $router: mockRouter,
        },
      },
    });

    const signupBtn = wrapper.find("#calc");

    await signupBtn.trigger("click");

    //tester at router pusher til Calculator
    expect(mockRouter.push).toHaveBeenCalledWith({
      name: "Calculator",
    });
  });
});
