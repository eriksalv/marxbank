<template>
  <main class="w-1/4">
    <h1>Fra</h1>
    <SearchBar
      ref="inputFromAccount"
      :reciever="false"
      :data="filterAccountsByUserIdAndName(getUserId, fromSearchTerm)"
      @termChanged="onFromTermChanged"
      @accountSelected="onFromAccountSelected" />
    <h1>Til</h1>
    <SearchBar
      ref="inputFromAccount"
      :reciever="true"
      :data="filterAccountsByName(recieverSearchTerm)"
      @termChanged="onRecieverTermChanged"
      @accountSelected="onRecieverAccountSelected" />
    <h1>Kroner</h1>
    <input v-model="amount" type="number" className="input" />
    <button class="button" @click="commitTransaction">Utfør transaksjon</button>
  </main>
</template>

<script lang="ts">
import SearchBar from "../components/SearchBar.vue";
import { mapGetters, mapActions } from "vuex";
import { Account, TransactionRequest } from "../types/types";
import { defineComponent } from "@vue/runtime-core";
// import { TransactionRequest } from '../types/types';

export default defineComponent({
  name: "CreateTransaction",
  components: {
    SearchBar,
  },
  data() {
    return {
      selectedRecieverAccount: {} as Account,
      recieverSearchTerm: "",
      selectedFromAccount: {} as Account,
      fromSearchTerm: "",
      amount: 0,
    };
  },
  computed: {
    ...mapGetters([
      "allAccounts",
      "filterAccountsByName",
      "filterAccountsByUserIdAndName",
      "getUserId",
    ]),
  },
  methods: {
    ...mapActions(["createTransaction", "fetchAccounts"]),
    /**
     * Sets the selected account as reciever and
     * resets the searchTerm
     * @param account selected account
     */
    onRecieverAccountSelected(account: Account) {
      this.selectedRecieverAccount = account;
      this.recieverSearchTerm = "";
    },
    /**
     * Sets the selected account as sender and
     * resets the searchTerm
     * @param account selected account
     */
    onFromAccountSelected(account: Account) {
      this.selectedFromAccount = account;
      this.fromSearchTerm = "";
    },
    onRecieverTermChanged(searchTerm: string) {
      this.recieverSearchTerm = searchTerm;
    },
    onFromTermChanged(searchTerm: string) {
      this.fromSearchTerm = searchTerm;
      console.log(searchTerm);
    },
    commitTransaction() {
      const transactionRequest: TransactionRequest = {
        from: this.selectedFromAccount.id,
        to: this.selectedRecieverAccount.id,
        amount: this.amount,
      };
      this.createTransaction(transactionRequest).catch((err: any) =>
        console.log(err)
      );
    },
  },
  async created() {
    await this.fetchAccounts();
  },
});
</script>

<style></style>
