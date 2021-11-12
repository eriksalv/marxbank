<template>
  <div className="search">
    <div className="searchInputs">
      <input
        @input="onInput"
        ref="inputField"
        type="text"
        :placeholder="placeholder"
        className="input" />
    </div>
    <div className="dataResult">
      <div
        v-for="account in data"
        :key="account.id"
        className="dataItem"
        @click="onSelectAccount(account)">
        <p class="font-bold">{{ account.name }}</p>
        <p>{{ account.accNumber }}</p>
      </div>
    </div>
    <div class="textbox">
      <p class="float-left">Konto: {{ selectedAccount.accNumber }}</p>
      <p v-if="!reciever" class="float-right">
        {{ selectedAccount.balance }} kr
      </p>
      <div class="clear-both"></div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "SearchBar",
  methods: {
    ...mapActions(["fetchAccounts"]),
    onInput(event) {
      this.$emit("termChanged", event.target.value);
    },
    onSelectAccount(account) {
      this.selectedAccount = account;
      this.placeholder = account.name;
      this.$refs.inputField.value = "";
      this.$emit("accountSelected", account);
    },
  },
  props: {
    data: Array,
    /**
     * if the searchbar is for the reciever account,
     * additional information should not be displayed
     */
    reciever: Boolean,
  },
  data() {
    return {
      selectedAccount: {},
      placeholder: "Velg en konto...",
    };
  },
};
</script>

<style></style>
