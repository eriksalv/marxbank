<template>
<main class="w-1/4">
    <h1>Fra</h1>
    <SearchBar ref="inputFromAccount" :reciever="false" 
        :data="filterAccountsByUserIdAndName(2, fromSearchTerm)" 
        @termChanged="onFromTermChanged" 
        @accountSelected="onFromAccountSelected"/>
    <h1>Til</h1>
    <SearchBar ref="inputFromAccount" :reciever="true"
        :data="filterAccountsByName(recieverSearchTerm)" 
        @termChanged="onRecieverTermChanged" 
        @accountSelected="onRecieverAccountSelected"/>
    <h1>Kroner</h1>
    <input type="number" ref="amount" className="input">
    <button @click="commitTransaction" class="bg-green-500 hover:bg-green-400 
        text-white font-bold py-2 px-4 border-b-4 border-green-700 
        hover:border-green-500 rounded w-full mt-6">
            Utfør transaksjon
    </button>
</main>
</template>

<script>
import SearchBar from '@/components/SearchBar.vue';
import { mapGetters } from 'vuex';
import Account from '@/store/modules/accounts.ts';

export default {
    name: 'CreateTransaction',
    components: {
        SearchBar
    },
    computed: {
        ...mapGetters(["allAccounts"]),
        ...mapGetters(["filterAccountsByName"]),
        ...mapGetters(["filterAccountsByUserIdAndName"])
    },
    methods: {
        /**
         * Sets the selected account as reciever and
         * resets the searchTerm
         * @param account selected account
        */
        onRecieverAccountSelected(account) {
            this.selectedRecieverAccount = account;
            this.recieverSearchTerm = "";
        },
        /**
         * Sets the selected account as sender and
         * resets the searchTerm
         * @param account selected account
        */
        onFromAccountSelected(account) {
            this.selectedFromAccount = account;
            this.fromSearchTerm = "";
        },
        onRecieverTermChanged(searchTerm) {
            this.recieverSearchTerm = searchTerm;
        },
        onFromTermChanged(searchTerm) {
            this.fromSearchTerm = searchTerm;
        },
        commitTransaction() {
            //TODO: utfør transaksjon...
            console.log(this.$refs.amount.value);
        }
    },
    data() {
        return {
            selectedRecieverAccount: Account,
            recieverSearchTerm: "",
            selectedFromAccount: Account,
            fromSearchTerm: "",
        }
    }
}
</script>

<style>

</style>