<template>
  <main class="flex flex-row flex-wrap max-w-[80%] justify-center">
    <div class="bg-white shadow overflow-hidden sm:rounded-lg min-w-full">
    <div>
    <h1 class="title">
      {{selectedAccount.name}}
    </h1>
    <p class="mt-1 text-sm text-gray-500 text-center">
      Detaljer
    </p>
    </div>
    <div class="mt-5 border-t border-gray-200">
    <dl class="divide-y divide-gray-200">
      <div class="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4">
        <dt class="text-sm font-medium text-gray-500">
          Disponibelt beløp
        </dt>
        <dd class="mt-1 flex text-sm text-gray-900 sm:mt-0 sm:col-span-2">
          <span class="flex-grow">{{selectedAccount.balance}} kr</span>
        </dd>
      </div>
      <div class="py-4 sm:grid sm:py-5 sm:grid-cols-3 sm:gap-4">
        <dt class="text-sm font-medium text-gray-500">
          Kontotype
        </dt>
        <dd class="mt-1 flex text-sm text-gray-900 sm:mt-0 sm:col-span-2">
          <span class="flex-grow">{{selectedAccount.type}}</span>
        </dd>
      </div>
      <div class="py-4 sm:grid sm:py-5 sm:grid-cols-3 sm:gap-4">
        <dt class="text-sm font-medium text-gray-500">
          Kontonummer
        </dt>
        <dd class="mt-1 flex text-sm text-gray-900 sm:mt-0 sm:col-span-2">
          <span class="flex-grow">{{selectedAccount.accNumber}}</span>
        </dd>
      </div>
      <div class="py-4 sm:grid sm:py-5 sm:grid-cols-3 sm:gap-4">
        <dt class="text-sm font-medium text-gray-500">
          Rente
        </dt>
        <dd class="mt-1 flex text-sm text-gray-900 sm:mt-0 sm:col-span-2">
          <span class="flex-grow">{{selectedAccount.interest}} %</span>
        </dd>
      </div>
    </dl>
  </div>
  </div>
  <h1 class="title">Nylig aktivitet</h1>
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