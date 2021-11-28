<template>
  <main class="w-1/4">
    <Alert :message="successMsg" @onHideAlert="successMsg = null" />
    <ErrorAlert :message="errorMsg" @onHideAlert="errorMsg = null" />
    <h1>Fra</h1>
    <SearchBar
      id="fromAccount"
      ref="inputFromAccount"
      :reciever="false"
      :data="filterAccountsByUserIdAndName(getUserId, fromSearchTerm)"
      :placeholder="'Velg en konto...'"
      @termChanged="onFromTermChanged"
      @accountSelected="onFromAccountSelected" />
    <h1>Til</h1>
    <SearchBar
      id="toAccount"
      ref="inputToAccount"
      :reciever="true"
      :data="filterAccountsByName(recieverSearchTerm)"
      :placeholder="'Velg en konto eller søk etter id...'"
      @termChanged="onRecieverTermChanged"
      @accountSelected="onRecieverAccountSelected" />
    <h1>Kroner</h1>
    <input v-model="amount" type="number" className="input" />
    <button class="button" @click="commitTransaction">Utfør transaksjon</button>
  </main>
</template>

<script lang="ts">
import SearchBar from "../components/SearchBar.vue";
import Alert from "../components/global/Alert.vue";
import ErrorAlert from "../components/global/ErrorAlert.vue";
import { mapGetters, mapActions } from "vuex";
import { Account, TransactionRequest } from "../types/types";
import { defineComponent } from "@vue/runtime-core";

export default defineComponent({
  components: {
    SearchBar,
    Alert,
    ErrorAlert,
  },
  data() {
    return {
      selectedRecieverAccount: {} as Account,
      recieverSearchTerm: "",
      selectedFromAccount: {} as Account,
      fromSearchTerm: "",
      amount: 0,
      errorMsg: null as unknown,
      successMsg: null as unknown,
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
  async created() {
    await this.fetchAccounts();
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
      this.errorMsg = null;
      this.successMsg = null;
      const transactionRequest: TransactionRequest = {
        from: this.selectedFromAccount.id,
        to: this.selectedRecieverAccount.id,
        amount: this.amount,
      };
      this.createTransaction(transactionRequest)
        .then(() => {
          this.successMsg = "Transaction successfully commited";
        })
        .catch((err: Error) => (this.errorMsg = err.message));
    },
  },
});
</script>

<style></style>
