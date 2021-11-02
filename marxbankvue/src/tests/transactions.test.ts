import { getters } from "../store/modules/transactions/getters";

const rootState = {
  message: "hello",
};

const testState = {
  transactions: [
    {
      date: "22-10-2021",
      from: 1,
      to: 2,
      amount: 200,
    },
    {
      date: "20-10-2021",
      from: 3,
      to: 1,
      amount: 50,
    },
    {
      date: "11-10-2021",
      from: 5,
      to: 6,
      amount: 149,
    },
  ],
};

test("test getters", () => {
  expect(getters.allTransactions(testState, null, rootState, null)).toBe(
    testState.transactions
  );
  //   expect(
  //     getters.filterTransactionsByAccount(testState, 1, rootState, null).length
  //   ).toBe(2);
});
