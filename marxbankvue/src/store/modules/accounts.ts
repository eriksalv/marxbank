export interface Account {
  readonly id: number;
  name: string;
  readonly accNumber: number;
  balance: number;
  interest: number;
  type: string;
}

const state = {
  accounts: [
    {
      id: 1,
      name: "test",
      accNumber: 200,
      balance: 200,
      interest: 3.0,
      type: "Sparekonto",
    },
    {
      id: 2,
      name: "acc2",
      accNumber: 300,
      balance: 200,
      interest: 2.4,
      type: "Marxkonto",
    },
    {
      id: 3,
      name: "acc1",
      accNumber: 200,
      balance: 200,
      interest: 3.0,
      type: "Sparekonto",
    },
    {
      id: 4,
      name: "Account",
      accNumber: 300,
      balance: 200,
      interest: 2.4,
      type: "Marxkonto",
    },
    {
      id: 5,
      name: "yay",
      accNumber: 200,
      balance: 200,
      interest: 3.0,
      type: "Sparekonto",
    },
    {
      id: 6,
      name: "yeet",
      accNumber: 300,
      balance: 200,
      interest: 2.4,
      type: "Marxkonto",
    },
    {
      id: 7,
      name: "why",
      accNumber: 200,
      balance: 200,
      interest: 3.0,
      type: "Sparekonto",
    },
    {
      id: 8,
      name: "hmm",
      accNumber: 300,
      balance: 200,
      interest: 2.4,
      type: "Marxkonto",
    },
    {
      id: 9,
      name: "fak",
      accNumber: 200,
      balance: 200,
      interest: 3.0,
      type: "Sparekonto",
    },
    {
      id: 10,
      name: "nice",
      accNumber: 300,
      balance: 200,
      interest: 2.4,
      type: "Marxkonto",
    },
  ],
};

const getters = {
  allAccounts: (state: { accounts: Array<Account> }) => state.accounts,
  /**
   * Filters accounts by name. The filter passes as long as
   * the account name icludes the filter string, such that it does not
   * have to match exactly.
   * @param state accounts to perform filter on
   * @param filter String value to filter accounts on
   * @returns filtered array of accounts
   */
  filterAccountsByName: (state: { accounts: Array<Account> }) => (
    filter: String
  ) => {
    if (!filter) {
      return [];
    }
    return state.accounts.filter((account) => {
      return account.name
        .toLowerCase()
        .includes(filter.toString().toLowerCase());
    });
  },
};

const actions = {};

const mutations = {};

export default {
  state,
  getters,
  actions,
  mutations,
};
