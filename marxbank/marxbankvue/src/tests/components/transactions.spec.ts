import { AccountState } from "@/store/modules/accounts/types";
import { createStore, Store } from "vuex";
import { Plugin } from "@vue/runtime-core";
import { mount, VueWrapper } from "@vue/test-utils";
import CreateTransaction from "../../views/CreateTransaction.vue";
import SearchBar from "../../components/SearchBar.vue";

describe("createTransactionView", () => {
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
  let mockAllAccounts: jest.Mock<any, any>;
  let mockFilterAccountsByName: jest.Mock<any, any>;
  let mockFilterAccountsByUserIdAndName: jest.Mock<any, any>;
  let mockGetUserId: jest.Mock<any, any>;
  let mockCreateTransaction: jest.Mock<any, any>;
  let mockFetchAccounts: jest.Mock<any, any>;
  let store: Store<AccountState> | Plugin | [Plugin, ...any[]];

  beforeEach(() => {
    mockAllAccounts = jest.fn().mockReturnValue(initState.accounts[0]);
    mockFilterAccountsByName = jest.fn().mockReturnValue(initState.accounts);
    mockFilterAccountsByUserIdAndName = jest
      .fn()
      .mockImplementation((a: number, b: string) => {
        return initState.accounts;
      });
    mockGetUserId = jest.fn().mockReturnValue(1);
    mockCreateTransaction = jest.fn();
    mockFetchAccounts = jest.fn();

    store = createStore({
      state() {
        initState;
      },
      getters: {
        allAccounts: () => mockAllAccounts,
        getUserId: () => mockGetUserId,
        filterAccountsByName: () => mockFilterAccountsByName,
        filterAccountsByUserIdAndName: () => mockFilterAccountsByUserIdAndName,
      },
      actions: {
        fetchAccounts: mockFetchAccounts,
        createTransaction: mockCreateTransaction,
      },
    });
  });

  test("test initial state", async () => {
    const wrapper = mount(CreateTransaction, {
      global: { plugins: [store] },
    });

    const dataitems = wrapper.findAll("div.dataItem");
    expect(dataitems).toHaveLength(4);
    expect(mockFetchAccounts).toHaveBeenCalled();
    expect(mockFilterAccountsByUserIdAndName).toHaveBeenCalled();
    expect(mockFilterAccountsByName).toHaveBeenCalled();
  });

  test("test on select account", async () => {
    const wrapper = mount(CreateTransaction, {
      global: { plugins: [store] },
    });

    wrapper.vm.onFromAccountSelected(initState.accounts[0]);
    expect(wrapper.vm.$data.selectedFromAccount).toEqual(initState.accounts[0]);
    wrapper.vm.onRecieverAccountSelected(initState.accounts[1]);
    expect(wrapper.vm.$data.selectedRecieverAccount).toEqual(
      initState.accounts[1]
    );
  });

  test("test on term changed", async () => {
    const wrapper = mount(CreateTransaction, {
      global: { plugins: [store] },
    });

    wrapper.find("#fromAccount").find("input").setValue("test");
    expect(wrapper.vm.$data.fromSearchTerm).toEqual("test");
    wrapper.find("#toAccount").find("input").setValue("test2");
    expect(wrapper.vm.$data.recieverSearchTerm).toEqual("test2");
  });

  test("test commit transaction", async () => {
    const wrapper = mount(CreateTransaction, {
      global: { plugins: [store] },
    });

    wrapper.vm.onFromAccountSelected(initState.accounts[0]);
    wrapper.vm.onRecieverAccountSelected(initState.accounts[1]);
    wrapper.find("input[type=number]").setValue(100);
    wrapper.vm.commitTransaction();
    expect(mockCreateTransaction).toHaveBeenCalled();
  });
});
