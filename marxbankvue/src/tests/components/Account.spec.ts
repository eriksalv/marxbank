import { AccountState } from "../../store/modules/accounts/types";
import { mount } from "@vue/test-utils";
import { createStore, Store } from "vuex";
import AccountList from "../../components/AccountList.vue";
import Account from "../../components/Account.vue";
import AccountInfo from "../../views/AccountInfo.vue";
import { Plugin } from "@vue/runtime-core";
import { TransactionState } from "../../store/modules/transactions/types";
import flushPromises from "flush-promises";

describe("AccountList", () => {
  const initState: AccountState = {
    accountStatus: "",
    accounts: [
      {
        id: 1,
        userId: 1,
        balance: 200,
        type: "Sparekonto",
        name: "test",
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
  let mockFilterAccountsByUserId: jest.Mock<any, any>;
  let mockGetUserId: jest.Mock<any, any>;
  let store: Store<AccountState> | Plugin | [Plugin, ...any[]];

  beforeEach(() => {
    mockFilterAccountsByUserId = jest.fn().mockReturnValue(initState.accounts);
    mockGetUserId = jest.fn();
    store = createStore({
      state: initState,
      getters: {
        filterAccountsByUserId: () => mockFilterAccountsByUserId,
        getUserId: mockGetUserId,
      },
    });
  });

  test("test initial state", () => {
    const wrapper = mount(AccountList, {
      global: { plugins: [store] },
    });

    expect(wrapper.html()).toContain("test");
    expect(wrapper.html()).toContain("test2");
    expect(mockFilterAccountsByUserId).toHaveBeenCalled();
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
        balance: 200,
        type: "Sparekonto",
        name: "test",
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
  let mockGetAccountById: jest.Mock<any, any>;
  let mockFetchAccountById: jest.Mock<any, any>;
  let mockFetchAccountsByTransactions: jest.Mock<any, any>;
  let mockDeposit: jest.Mock<any, any>;
  let mockWithdraw: jest.Mock<any, any>;

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
  let mockFetchTransactionsByAccount: jest.Mock<any, any>;
  let store: Store<any> | Plugin | [Plugin, ...any[]];

  beforeEach(() => {
    //accountStore setup
    mockGetAccountById = jest
      .fn()
      .mockReturnValue(initAccountState.accounts[0]);
    mockFetchAccountById = jest.fn();
    mockFetchAccountsByTransactions = jest.fn();
    mockDeposit = jest.fn().mockImplementation(() => {
      if (initAccountState.accounts[0].balance !== null) {
        initAccountState.accounts[0].balance += 100;
      }
    });
    mockWithdraw = jest.fn().mockImplementation(() => {
      if (initAccountState.accounts[0].balance !== null) {
        initAccountState.accounts[0].balance -= 200;
      }
    });

    //transactionStore setup
    mockFilterTransactionsByAccount = jest
      .fn()
      .mockReturnValue(initTransactionState.transactions);
    mockFetchTransactionsByAccount = jest.fn();

    store = createStore({
      state: {
        initTransactionState,
        initAccountState,
      },
      getters: {
        getAccountById: () => mockGetAccountById,
        filterTransactionsByAccount: () => mockFilterTransactionsByAccount,
      },
      actions: {
        fetchAccountById: mockFetchAccountById,
        fetchTransactionsByAccount: mockFetchTransactionsByAccount,
        fetchAccountsByTransactions: mockFetchAccountsByTransactions,
        deposit: mockDeposit,
        withdraw: mockWithdraw,
      },
    });
  });

  test("test initial state", async () => {
    const wrapper = mount(AccountInfo, {
      global: { plugins: [store] },
    });

    //Blir kalt 2 ganger av Transaction.vue
    expect(mockGetAccountById).toHaveBeenCalledTimes(2);
    //Blir kalt en gang når AccountInfo mountes
    expect(mockFilterTransactionsByAccount).toHaveBeenCalledTimes(1);

    //Flusher kall til fetchAccountById og fetchTransactionsByAccount
    //i created-metode
    await flushPromises();

    expect(mockFetchTransactionsByAccount).toHaveBeenCalledTimes(1);
    expect(mockFetchAccountsByTransactions).toHaveBeenCalledTimes(1);
    expect(mockFetchAccountById).toHaveBeenCalledTimes(1);
    //Blir kalt en gang til når promises er flushet
    expect(mockGetAccountById).toHaveBeenCalledTimes(3);
    expect(wrapper.html()).toContain("200 kr");
    expect(wrapper.vm.$data.selectedAccount).toEqual(
      initAccountState.accounts[0]
    );
    expect(wrapper.vm.$data.amount).toEqual(Number);
    expect(wrapper.vm.$data.showModal).toBe(false);
  });

  test("test deposit", async () => {
    const wrapper = mount(AccountInfo, {
      global: { plugins: [store] },
    });

    expect(initAccountState.accounts[0].balance).toEqual(200);

    await wrapper.find("#showModal").trigger("click");

    expect(wrapper.vm.$data.showModal).toEqual(true);

    await wrapper.find("input").setValue(100);

    expect(wrapper.vm.$data.amount).toEqual(100);

    await wrapper.find("#deposit").trigger("click");

    expect(wrapper.vm.$data.showModal).toEqual(false);
    expect(mockDeposit).toHaveBeenCalledTimes(1);
    expect(mockWithdraw).toHaveBeenCalledTimes(0);
    expect(initAccountState.accounts[0].balance).toEqual(300);

    await wrapper.vm.$forceUpdate();

    expect(wrapper.text()).toContain("300 kr");
  });

  test("test withdraw", async () => {
    const wrapper = mount(AccountInfo, {
      global: { plugins: [store] },
    });

    await wrapper.find("#showModal").trigger("click");
    await wrapper.find("input").setValue(200);
    await wrapper.find("#withdraw").trigger("click");

    expect(mockDeposit).toHaveBeenCalledTimes(0);
    expect(mockWithdraw).toHaveBeenCalledTimes(1);
    expect(initAccountState.accounts[0].balance).toEqual(100);

    await wrapper.vm.$forceUpdate();

    expect(wrapper.text()).toContain("100 kr");
  });
});
