<template>
  <div class="flex flex-row flex-wrap min-w-[80%]">
    <TransactionList class="min-w-max"></TransactionList>
  </div>
</template>

<script>
// @ is an alias to /src
import TransactionList from '../components/TransactionList.vue'

export default {
  name: 'MyTransactions',
  components: {
    TransactionList
  }
}
</script>