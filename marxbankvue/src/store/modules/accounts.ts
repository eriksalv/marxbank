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
      name: "acc1",
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
  ],
};

const getters = {
  allAccounts: (state: { accounts: Array<Account> }) => state.accounts,
  filterAccountsByName: (state: { accounts: Array<Account> }) => (
    filter: String
  ) => {
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
