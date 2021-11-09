import { RootState } from "@/store/types";
import { getters } from "../store/modules/transactions/getters";
import { mutations } from "../store/modules/transactions/mutations";
import { Status } from "../store/types";

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
