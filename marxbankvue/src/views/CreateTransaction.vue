<template>
<main class="w-1/4">
    <h1>Velg en konto</h1>
    <div className="search">
        <SearchBar ref="inputAccount" @termChanged="onTermChanged" :placeholder="recieverPlaceholder"/>
        <div className="dataResult">
            <div v-for="account in filterAccountsByName(searchTerm)" :key="account.id"
                className="dataItem" @click="selectAccount(account)">
                <p class="font-bold">{{ account.name }}</p>
                <p>{{ account.accNumber }}</p>
            </div>
        </div>
    </div>
</main>
</template>

<script>
import SearchBar from '@/components/SearchBar.vue';
import { mapGetters } from 'vuex';
import Account from '@/store/modules/accounts.ts';

export default {
    name: 'CreateTransaction',
    components: {
        SearchBar,
    },
    computed: mapGetters(["filterAccountsByName"]),
    methods: {
        selectAccount(account) {
            //TODO: vis info om kontoen (kontonummer)
            this.selectedRecieverAccount = account;
            this.recieverPlaceholder = account.name;
            this.searchTerm = "";
            this.$refs.inputAccount.clearInput();
            console.log(account);
        },
        onTermChanged(searchTerm) {
            this.searchTerm = searchTerm;
            console.log(this.searchTerm);
        }

    },
    data() {
        return {
            selectedRecieverAccount: Account,
            recieverPlaceholder: "Søk...",
            searchTerm: "",
        }
    }
}
</script>

<style>

</style>