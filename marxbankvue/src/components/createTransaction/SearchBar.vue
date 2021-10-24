<template>
  <!--<input type="text" @input="onInput" placeholder="Søk etter navn på konto..." 
        class="box-border border-none focus:border-b w-full py-4 px-6 text-gray-700 leading-tight">-->
    <div className="search">
        <div className="searchInputs">
            <input v-model="searchTerm" ref="inputAccount" type="text" placeholder="Søk..." className="input"/>
        </div>
        <div className="dataResult">
            <div v-for="account in filterAccountsByName(searchTerm)" :key="account.id"
                className="dataItem" @click="selectAccount(account)"><p class="font-bold">{{ account.name }}</p><p>{{ account.accNumber }}</p></div>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: "SearchBar",
    computed: mapGetters(["filterAccountsByName"]),
    methods: {
        /**
         * Sets the selected account name as placeholder text for
         * input field and resets the searchterm. 
        */
        selectAccount(account) {
            this.$refs.inputAccount.placeholder = account.name;
            this.$emit("accountSelected", account);
            this.searchTerm = "";
        }
    },
    data() {
        return {
            searchTerm: ""
        }
    }
}
</script>

<style>

</style>