import { AccountState } from "@/store/modules/accounts/types";
import { RootState } from "@/store/types";
import { getters } from "../store/modules/accounts/getters";
import { mutations } from "../store/modules/accounts/mutations";
import axios from "axios";
import AxiosMockAdapter from "axios-mock-adapter";
import { actions } from "../store/modules/accounts/actions";
import { Account, DepositWithdrawRequest } from "../types/types";
import { Getter, GetterTree } from "vuex";

const rootState: RootState = {
  message: "",
};

const testState: AccountState = {
  accountStatus: "",
  accounts: [
    {
      id: 1,
      userId: 1,
      name: "test",
      accNumber: 200,
      balance: 200,
      interest: 3.0,
      type: "Sparekonto",
    },
    {
      id: 2,
      userId: 2,
      name: "test2",
      accNumber: 201,
      balance: 200,
      interest: 3.0,
      type: "Sparekonto",
    },
  ],
};

describe("getters", () => {
  it("test get all accounts", () => {
    const actual = getters.allAccounts(testState, null, rootState, null);

    expect(actual).toEqual(testState.accounts);
  });
  it("test filter accounts by name", () => {
    const empty = getters.filterAccountsByName(
      testState,
      null,
      rootState,
      null
    )("");

    expect(empty).toEqual([]);

    const test = getters.filterAccountsByName(
      testState,
      null,
      rootState,
      null
    )("test");

    expect(test).toEqual(testState.accounts);

    const test2 = getters.filterAccountsByName(
      testState,
      null,
      rootState,
      null
    )("test2");

    expect(test2).toEqual([testState.accounts[1]]);
  });
  it("test filter accounts by user id", () => {
    const empty = getters.filterAccountsByUserId(
      testState,
      null,
      rootState,
      null
    )(3);

    expect(empty).toEqual([]);

    const actual = getters.filterAccountsByUserId(
      testState,
      null,
      rootState,
      null
    )(2);

    expect(actual).toEqual([testState.accounts[1]]);
  });
  it("test get account by id", () => {
    const noMatch = getters.getAccountById(testState, null, rootState, null)(3);

    expect(noMatch).toBeUndefined();

    const actual = getters.getAccountById(testState, null, rootState, null)(1);

    expect(actual).toEqual(testState.accounts[0]);
  });
});

describe("mutations", () => {
  it("test set accounts", () => {
    const newAccounts = [
      {
        id: 1,
        userId: 1,
        name: "test",
        accNumber: 200,
        balance: 200,
        interest: 3.0,
        type: "Sparekonto",
      },
    ];

    mutations.setAccounts(testState, newAccounts);

    expect(testState.accounts).toEqual(newAccounts);
  });

  it("test add account", () => {
    const newAccount = {
      id: 2,
      userId: 1,
      name: "test2",
      accNumber: 201,
      balance: 200,
      interest: 3.0,
      type: "Sparekonto",
    };

    const oldAccounts = testState.accounts;

    mutations.addAccount(testState, newAccount);

    expect(testState.accounts).toEqual([...oldAccounts, newAccount]);
  });

  it("test update account", () => {
    const updateAccount = {
      id: 2,
      userId: 1,
      name: "updated",
      accNumber: 201,
      balance: 200,
      interest: 3.0,
      type: "Sparekonto",
    };

    mutations.updateAccount(testState, updateAccount);

    expect(updateAccount.name).toEqual(testState.accounts[1].name); 
  });

  it("test set account status", () => {
    mutations.setAccountStatus(testState, "error");

    expect("error").toEqual(testState.accountStatus);
  });
});

describe("actions", () => {
  let mock: AxiosMockAdapter;

  beforeAll(() => {
    mock = new AxiosMockAdapter(axios);
  });

  afterEach(() => {
    mock.reset();
  });

  describe("fetch by id", () => {
    it("new account", async () => {
      const commit = jest.fn();
      const rootGetters = {
        allAccounts: [
          {
            id: 2,
            userId: 1,
            name: "test2",
            accNumber: 201,
            balance: 200,
            interest: 3.0,
            type: "Sparekonto",
          },
        ],
      };
      const request: number = 3;
      const response: any = {
        id: 3,
        accountNumber: 96,
        type: "Sparekonto",
        user: 1,
        name: "test",
        balance: 500,
        interestRate: 5.0,
      };
      const expected: Account = {
        id: 3,
        userId: 1,
        accNumber: 96,
        name: "test",
        balance: 500,
        interest: 5.0,
        type: "Sparekonto",
      };
      const fetchAccountById = actions.fetchAccountById as Function;
      mock.onGet(`/accounts/myAccounts/${request}`).reply(200, response);

      await fetchAccountById({ commit, rootGetters }, request).then(() => {
        expect(commit).toHaveBeenCalledTimes(3);
        expect(commit).toHaveBeenCalledWith("setAccountStatus", "loading");
        expect(commit).toHaveBeenCalledWith("setAccountStatus", "success");
        expect(commit).toHaveBeenCalledWith("addAccount", expected);
        expect(mock.history.get.length).toEqual(1);
        expect(mock.history.get[0].url).toEqual(`/accounts/myAccounts/3`);
      });
    });

    it("existing account", async () => {
      const commit = jest.fn();
      const rootGetters = {
        allAccounts: [
          {
            id: 2,
            userId: 1,
            name: "test2",
            accNumber: 201,
            balance: 200,
            interest: 3.0,
            type: "Sparekonto",
          },
        ],
      };
      const request: number = 2;
      const response: any = {
        id: 2,
        accountNumber: 201,
        type: "Sparekonto",
        user: 1,
        name: "test2",
        balance: 200,
        interestRate: 3.0,
      };
      const expected: Account = {
        id: 2,
        userId: 1,
        accNumber: 201,
        name: "test2",
        balance: 200,
        interest: 3.0,
        type: "Sparekonto",
      };
      const fetchAccountById = actions.fetchAccountById as Function;
      mock.onGet(`/accounts/myAccounts/${request}`).reply(200, response);

      await fetchAccountById({ commit, rootGetters }, request).then(() => {
        expect(commit).toHaveBeenCalledTimes(3);
        expect(commit).toHaveBeenCalledWith("setAccountStatus", "loading");
        expect(commit).toHaveBeenCalledWith("setAccountStatus", "success");
        expect(commit).toHaveBeenCalledWith("updateAccount", expected);
        expect(mock.history.get.length).toEqual(1);
        expect(mock.history.get[0].url).toEqual(`/accounts/myAccounts/2`);
      });
    });
  });

  describe("deposit", () => {
    it("deposit", async () => {
      const commit = jest.fn();
      const rootGetters = {
        allAccounts: [
          {
            id: 2,
            userId: 1,
            name: "test2",
            accNumber: 201,
            balance: 200,
            interest: 3.0,
            type: "Sparekonto",
          },
        ],
      };
      const depositRequest: DepositWithdrawRequest = {
        amount: 100,
        accountId: 2,
      }
      const response: any = {
        id: 2,
        accountNumber: 201,
        type: "Sparekonto",
        user: 1,
        name: "test2",
        balance: 300,
        interestRate: 3.0,
      };
      const expected: Account = {
        id: 2,
        userId: 1,
        accNumber: 201,
        name: "test2",
        balance: 300,
        interest: 3.0,
        type: "Sparekonto",
      };
      const deposit = actions.deposit as Function;
      mock.onPost(`/accounts/deposit`).reply(200, response);

      await deposit({ commit, rootGetters }, depositRequest).then(() => {
        expect(commit).toHaveBeenCalledTimes(3);
        expect(commit).toHaveBeenCalledWith("setAccountStatus", "loading");
        expect(commit).toHaveBeenCalledWith("setAccountStatus", "success");
        expect(commit).toHaveBeenCalledWith("updateAccount", expected);
        expect(mock.history.post.length).toEqual(1);
        expect(mock.history.post[0].url).toEqual(`/accounts/deposit`);
      });
    });
  })
});
