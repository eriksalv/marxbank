import { AccountState } from "../../store/modules/accounts/types";
import { mount } from "@vue/test-utils";
import { createStore, Store } from "vuex";
import AccountList from "../../components/AccountList.vue";
import Account from "../../components/Account.vue";
import AccountInfo from "../../views/AccountInfo.vue";
import { Plugin } from "@vue/runtime-core";
import { TransactionState } from "../../store/modules/transactions/types";

describe("AccountList", () => {
  const initState: AccountState = {
    accountStatus: "",
    accounts: [
      {
        id: 1,
        userId: 1,
        accNumber: 1,
        balance: 200,
        type: "Sparekonto",
        name: "test",
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
  let mockAllAccounts: jest.Mock<any, any>;
  let store: Store<AccountState> | Plugin | [Plugin, ...any[]];

  beforeEach(() => {
    mockAllAccounts = jest.fn().mockReturnValue(initState.accounts);
    store = createStore({
      state: initState,
      getters: {
        allAccounts: mockAllAccounts,
      },
    });
  });

  test("test initial state", () => {
    const wrapper = mount(AccountList, {
      global: { plugins: [store] },
    });

    expect(wrapper.html()).toContain("test");
    expect(wrapper.html()).toContain("test2");
    expect(mockAllAccounts).toHaveBeenCalled();
  });

  test("test showAccount", async () => {
    const mockRoute = {
      params: {
        id: 1,
      },
    };
    const mockRouter = {
      push: jest.fn(),
    };

    const wrapper = mount(AccountList, {
      global: {
        plugins: [store],
        mocks: {
          $route: mockRoute,
          $router: mockRouter,
        },
      },
    });

    await wrapper.findComponent(Account).vm.$emit("accountSelected", 1);

    //tester at router pusher til AccountInfo fane med parameter 1
    expect(mockRouter.push).toHaveBeenCalledTimes(1);
    expect(mockRouter.push).toHaveBeenCalledWith({
      name: "AccountInfo",
      params: { id: 1 },
    });
  });
});

describe("AccountInfo", () => {
  const initAccountState: AccountState = {
    accountStatus: "",
    accounts: [
      {
        id: 1,
        userId: 1,
        accNumber: 1,
        balance: 200,
        type: "Sparekonto",
        name: "test",
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
  let mockGetAccountById: jest.Mock<any, any>;
  let mockFetchAccountById: jest.Mock<any, any>;
  let mockDeposit: jest.Mock<any, any>;
  let mockWithdraw: jest.Mock<any, any>;
  let accountStore: Store<AccountState> | Plugin | [Plugin, ...any[]];

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
  let mockFilterTransactionsByAccount: jest.Mock<any, any>;
  let transactionStore: Store<TransactionState> | Plugin | [Plugin, ...any[]];
  let store: Store<any> | Plugin | [Plugin, ...any[]];

  beforeEach(() => {
    //accountStore setup
    mockGetAccountById = jest
      .fn()
      .mockReturnValue(initAccountState.accounts[0]);
    mockFetchAccountById = jest.fn();
    mockDeposit = jest.fn();
    mockWithdraw = jest.fn();
    accountStore = createStore({
      state: initAccountState,
      getters: {
        getAccountById: mockGetAccountById,
      },
      actions: {
        fetchAccountById: mockFetchAccountById,
        deposit: mockDeposit,
        withdraw: mockWithdraw,
      },
    });

    //transactionStore setup
    mockFilterTransactionsByAccount = jest
      .fn()
      .mockReturnValue(initTransactionState.transactions);
    transactionStore = createStore({
      state: initTransactionState,
      getters: {
        filterTransactionsByAccount: mockFilterTransactionsByAccount,
      },
    });

    store = createStore({
      state: {
        initTransactionState,
        initAccountState,
      },
      getters: {
        getAccountById: mockGetAccountById,
        filterTransactionsByAccount: mockFilterTransactionsByAccount,
      },
      actions: {
        fetchAccountById: mockFetchAccountById,
        deposit: mockDeposit,
        withdraw: mockWithdraw,
      },
    });
  });

  test("test initial state", () => {
    const wrapper = mount(AccountInfo, {
      global: { plugins: [store] },
    });

    expect(mockGetAccountById).toHaveBeenCalledTimes(1);
    expect(mockFetchAccountById).toHaveBeenCalledTimes(1);
    expect(mockFilterTransactionsByAccount).toHaveBeenCalledTimes(1);
  });
});
