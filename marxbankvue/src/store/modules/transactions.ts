export interface Transaction {
  readonly date: string;
  from: number;
  to: number;
  amount: number;
}

const state = {
  transactions: [
    {
      date: "22-10-2021",
      from: 1811,
      to: 2020,
      amount: 200,
    },
    {
      date: "20-10-2021",
      from: 2020,
      to: 1512,
      amount: 50,
    },
    {
      date: "11-10-2021",
      from: 2020,
      to: 1288,
      amount: 149,
    },
  ],
};

const getters = {
  allTransactions: (state: { transactions: Transaction }) => state.transactions,
};

const actions = {};

const mutations = {};

export default {
  state,
  getters,
  actions,
  mutations,
};

