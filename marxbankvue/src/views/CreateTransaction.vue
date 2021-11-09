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
    <input type="number" v-model="amount" className="input">
    <button @click="commitTransaction" class="button">
            Utfør transaksjon
    </button>
</main>
</template>

<script>
import SearchBar from '../components/SearchBar.vue';
import { mapGetters, mapActions } from 'vuex';

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
        ...mapActions(["createTransaction"]),
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
            const transactionRequest = {
                from: this.selectedFromAccount.id,
                to: this.selectedRecieverAccount.id,
                amount: this.amount
            }
            console.log(transactionRequest)
            //Dette fungerer foreløpig ikke, siden accounts modul ikke er satt opp enda
            this.createTransaction(transactionRequest).catch(err => console.log(err));
        }
    },
    data() {
        return {
            selectedRecieverAccount: Object,
            recieverSearchTerm: "",
            selectedFromAccount: Object,
            fromSearchTerm: "",
            amount: Number
        }
    }
}
</script>

<style>

</style>