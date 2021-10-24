<template>
  <div class="w-full overflow-auto h-80">
    <table class="w-full border-collapse">
      <thead>
        <tr class="text-xs font-semibold tracking-wide text-center text-gray-800 uppercase border-b dark:border-gray-700 bg-gray-50 dark:text-gray-400 dark:bg-gray-800">
          <th class="th px-4 py-3">Dato</th>
          <th class="th px-4 py-3">Fra konto</th>
          <th class="th px-4 py-3">Til konto</th>
          <th class="th px-4 py-3">Beløp</th>
        </tr>
      </thead>
      <tbody class="bg-white divide-y dark:divide-gray-700 dark:bg-gray-800">
        <Transaction @click="showTransaction(transaction.amount)" 
          :date="transaction.date" 
          :from="transaction.from" 
          :to="transaction.to"
          :amount="transaction.amount"
          v-for="transaction in allTransactions" :key="transaction.amount" 
          class="transaction" />
      </tbody>
    </table>
  </div>
</template>

<script>
import Transaction from './Transaction.vue';
import { mapGetters } from 'vuex';

export default {
    name: 'TransactionList',
    computed: mapGetters(['allTransactions']),
    components: {
      Transaction
    },
    methods: {
      showTransaction(amount) {
        console.log(amount)
      }
    }
};
</script>

<style>

</style>