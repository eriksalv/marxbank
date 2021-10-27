import accounts from "../store/modules/accounts";

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
    }
  ]
}

test('test length of test state', () => {
  expect(accounts.getters.allAccounts(testState).length).toBe(2);
});