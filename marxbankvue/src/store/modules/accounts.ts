const state = {
    accounts: [
        {
            id: 1,
            name: 'acc1',
            accNumber: 200,
            balance: 500,
            interest: 3.0,
            type: 'Sparekonto'
        },
        {
            id: 2,
            name: 'acc2',
            accNumber: 300,
            balance: 200,
            interest: 2.4,
            type: 'Marxkonto'
        }
    ]
};

const getters = {
    allAccounts: (state: { accounts: any; }) => state.accounts
};

const actions = {};

const mutations = {};

export default {
    state,
    getters,
    actions,
    mutations
};
