<template>
  <div class="flex flex-row flex-wrap min-w-[80%]">
    <TransactionList :transactions="allTransactions" class="min-w-max"></TransactionList>
  </div>
</template>

<script>
// @ is an alias to /src
import TransactionList from '../components/TransactionList.vue'
import {mapGetters} from 'vuex'

export default {
  created() {
    //TODO: fetch transactions fra api
  },
  name: 'MyTransactions',
  components: {
    TransactionList
  },
  computed: {
    ...mapGetters(['allTransactions'])
  }
}
</script>