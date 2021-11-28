<template>
  <div className="search">
    <div className="searchInputs">
      <input
        ref="inputField"
        type="text"
        :placeholder="placeholder"
        className="input"
        @input="onInput" />
    </div>
    <div className="dataResult">
      <div
        v-for="account in data"
        :key="account.id"
        className="dataItem"
        @click="onSelectAccount(account)">
        <p class="font-bold">
          {{ account.name }}
        </p>
        <p>{{ account.id }}</p>
      </div>
    </div>
    <button v-if="reciever" class="button w-1/3 mt-2" @click="searchForAccount">
      Søk
    </button>
    <p v-if="reciever && accountNotFound" class="text-red-500">
      Fant ikke konto med gitt id
    </p>
    <div class="textbox">
      <p class="float-left">Konto: {{ selectedAccount.id }}</p>
      <p v-if="!reciever" class="float-right">
        {{ selectedAccount.balance }} kr
      </p>
      <div class="clear-both" />
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name: "SearchBar",
  props: {
    data: {
      type: Array,
      default: () => [],
    },
    /**
     * if the searchbar is for the reciever account,
     * additional information should not be displayed
     */
    reciever: {
      type: Boolean,
      default: false,
    },
    placeholder: {
      type: String,
      default: "",
    },
  },
  emits: ["termChanged", "accountSelected"],
  data() {
    return {
      selectedAccount: {},
      accountNotFound: false,
    };
  },
  computed: {
    ...mapGetters(["getAccountById", "accountStatus"]),
  },
  methods: {
    ...mapActions(["fetchPublicAccountById"]),
    onInput(event) {
      this.$emit("termChanged", event.target.value);
    },
    onSelectAccount(account) {
      this.selectedAccount = account;
      this.$refs.inputField.value = "";
      this.$emit("accountSelected", account);
    },
    async searchForAccount() {
      const searchId = +this.$refs.inputField.value;
      console.log(searchId);
      await this.fetchPublicAccountById(searchId);
      if (this.accountStatus === "error") {
        this.accountNotFound = true;
        return;
      }
      this.accountNotFound = false;
      const account = this.getAccountById(parseInt(searchId));
      console.log(account);
      this.onSelectAccount(account);
    },
  },
};
</script>

<style></style>
