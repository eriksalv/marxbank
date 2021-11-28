import { RootState } from "@/store/types";
import { getters } from "../store/modules/transactions/getters";
import { mutations } from "../store/modules/transactions/mutations";
import { Status } from "../store/types";
import AxiosMockAdapter from "axios-mock-adapter";
import axios from "axios";
import { Transaction } from "../store/modules/transactions/types";
import { actions } from "../store/modules/transactions/actions";
import { TransactionRequest } from "../types/types";

const rootState: RootState = {
  message: "",
};

const status: Status = "";

const testState = {
  transactionStatus: status,
  transactions: [
    {
      id: 1,
      from: 1,
      to: 2,
      amount: 200,
      date: "22-10-2021",
    },
    {
      id: 2,
      from: 3,
      to: 1,
      amount: 50,
      date: "20-10-2021",
    },
    {
      id: 3,
      from: 5,
      to: 6,
      amount: 149,
      date: "11-10-2021",
    },
  ],
};

describe("getters", () => {
  it("test get all transactions", () => {
    const actual = getters.allTransactions(testState, null, rootState, null);

    expect(actual).toEqual(testState.transactions);
  });
  it("test filter transactions by account", () => {
    const actual = getters.filterTransactionsByAccount(
      testState,
      null,
      rootState,
      null
    )(1);

    expect(actual).toEqual([
      testState.transactions[0],
      testState.transactions[1],
    ]);
  });
});

describe("mutations", () => {
  it("test set transactions", () => {
    const newTransactions = [
      {
        date: "22-10-2021",
        from: 1,
        to: 2,
        amount: 200,
      },
    ];

    mutations.setTransactions(testState, newTransactions);

    expect(testState.transactions).toEqual(newTransactions);
  });

  it("test add transaction", () => {
    const newTransaction = {
      date: "20-10-2021",
      from: 3,
      to: 1,
      amount: 50,
    };

    const oldTransactions = testState.transactions;

    mutations.addTransaction(testState, newTransaction);

    expect(testState.transactions).toEqual([
      ...oldTransactions,
      newTransaction,
    ]);
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

  describe("fetch transactions", () => {
    const rootGetters = {
      allTransactions: [],
    };
    const response: any = [
      {
        id: 3,
        fromId: 1,
        recieverId: 2,
        amount: 100,
        transactionDate: "17-11-2021",
      },
    ];
    const expected: Array<Transaction> = [
      {
        id: 3,
        from: 1,
        to: 2,
        amount: 100,
        date: "17-11-2021",
      },
    ];
    it("fetch transactions", async () => {
      const commit = jest.fn();
      const fetchTransactions = actions.fetchTransactions as Function;
      mock.onGet(`/transactions/myTransactions`).reply(200, response);

      await fetchTransactions({ commit, rootGetters }).then(() => {
        expect(commit).toHaveBeenCalledTimes(3);
        expect(commit).toHaveBeenCalledWith("setTransactionStatus", {
          status: "loading",
        });
        expect(commit).toHaveBeenCalledWith("setTransactionStatus", {
          status: "success",
        });
        expect(commit).toHaveBeenCalledWith("setTransactions", expected);
        expect(mock.history.get.length).toEqual(1);
        expect(mock.history.get[0].url).toEqual(`/transactions/myTransactions`);
      });
    });
    it("fetch transactions by account", async () => {
      const commit = jest.fn();
      const fetchTransactionsByAccount =
        actions.fetchTransactionsByAccount as Function;
      mock.onGet(`/transactions/myTransactions/3`).reply(200, response);

      await fetchTransactionsByAccount({ commit, rootGetters }, 3).then(() => {
        expect(commit).toHaveBeenCalledTimes(3);
        expect(commit).toHaveBeenCalledWith("setTransactionStatus", {
          status: "loading",
        });
        expect(commit).toHaveBeenCalledWith("setTransactionStatus", {
          status: "success",
        });
        expect(commit).toHaveBeenCalledWith("setTransactions", expected);
        expect(mock.history.get.length).toEqual(1);
        expect(mock.history.get[0].url).toEqual(
          `/transactions/myTransactions/3`
        );
      });
    });
  });

  describe("create transaction", () => {
    it("create transaction", async () => {
      const commit = jest.fn();
      const rootGetters = {
        allTransactions: [],
      };
      const request: TransactionRequest = {
        from: 1,
        to: 2,
        amount: 100,
      };
      const response: any = {
        id: 3,
        fromId: 1,
        recieverId: 2,
        amount: 100,
        transactionDate: "17-11-2021",
      };
      const expected: Transaction = {
        id: 3,
        from: 1,
        to: 2,
        amount: 100,
        date: "17-11-2021",
      };
      const createTransaction = actions.createTransaction as Function;
      mock.onPost(`/transactions/transfer`).reply(200, response);

      await createTransaction({ commit, rootGetters }, request).then(() => {
        expect(commit).toHaveBeenCalledTimes(3);
        expect(commit).toHaveBeenCalledWith("setTransactionStatus", {
          status: "loading",
        });
        expect(commit).toHaveBeenCalledWith("setTransactionStatus", {
          status: "success",
        });
        expect(commit).toHaveBeenCalledWith("addTransaction", expected);
        expect(mock.history.post.length).toEqual(1);
        expect(mock.history.post[0].url).toEqual(`/transactions/transfer`);
      });
    });
  });
});
