<template>
  <main class="flex flex-row flex-wrap min-w-[80%]">
    <div class="flex items-center">
        <h1 class="ml-3 text-2xl font-bold leading-7 text-gray-600 sm:leading-9 sm:truncate">
        {{selectedAccount.name}}
        </h1>
    </div>
    <TransactionList :transactions="filterTransactionsByAccount(selectedAccount.id)" class="min-w-max"/>
</main>
</template>

<script>
import {mapGetters} from 'vuex';
import TransactionList from '@/components/TransactionList.vue'

export default {
    created() {
        //TODO: fetch kontoer fra api
        this.selectedAccount = this.$store.getters.getAccountById(parseInt(this.id))
        console.log(this.selectedAccount);
        console.log(this.$store.getters.filterTransactionsByAccount(this.selectedAccount))
    },
    props: ["id"],
    computed: {
        ...mapGetters(['getAccountById', 'filterTransactionsByAccount'])
    },
    components: {
        TransactionList
    },
    data() {
        return {
            selectedAccount: Object
        }
    }
}
</script>

<style>

</style>