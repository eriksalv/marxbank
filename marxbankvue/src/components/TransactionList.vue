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
        <Transaction  
          :date="transaction.date" 
          :from="transaction.from" 
          :to="transaction.to"
          :amount="transaction.amount"
          v-for="transaction in allTransactions" :key="transaction.id"
          class="transaction" />
      </tbody>
    </table>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
import Transaction from './Transaction.vue';

export default {
    name: 'TransactionList',
    components: {
      Transaction
    },
    computed: {
      ...mapGetters(["allTransactions"])
    }
};
</script>

<style>

</style>