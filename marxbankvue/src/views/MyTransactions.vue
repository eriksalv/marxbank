<template>
  <div class="flex flex-row flex-wrap min-w-[80%]">
    <TransactionList :transactions="allTransactions" class="min-w-max" />
  </div>
</template>

<script>
// @ is an alias to /src
import TransactionList from "../components/TransactionList.vue";
import { mapGetters, mapActions } from "vuex";

export default {
  name: "MyTransactions",
  components: {
    TransactionList,
  },
  computed: {
    ...mapGetters(["allTransactions"]),
  },
  async created() {
    await this.fetchAccounts();
    await this.fetchAccountsByTransactions();
    await this.fetchTransactions();
  },
  methods: {
    ...mapActions([
      "fetchTransactions",
      "fetchAccounts",
      "fetchAccountsByTransactions",
    ]),
  },
};
</script>
