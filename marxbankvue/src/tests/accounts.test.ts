import { getters } from "../store/modules/accounts/getters";

const rootState = {
  message: "hello",
};

const testState = {
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
      userId: 1,
      name: "test2",
      accNumber: 201,
      balance: 200,
      interest: 3.0,
      type: "Sparekonto",
    },
  ],
};

test("test length of test state", () => {
  expect(getters.allAccounts(testState, null, rootState, null).length).toBe(2);
});
