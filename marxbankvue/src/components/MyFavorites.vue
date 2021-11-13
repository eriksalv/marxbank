<template>
  <div>
    <MyFavorite
      v-for="account in allAccounts.slice(0, 3)"
      :key="account.id"
      :acc-number="account.accNumber"
      :balance="account.balance"
      :acc-name="account.name"
      :type="account.type"
      class="account"
      @click="showAccount(account.accNumber)" />
  </div>
</template>

<script>
import MyFavorite from "./MyFavorite.vue";
import { mapGetters } from "vuex";

export default {
  name: "MyFavorites",
  computed: mapGetters(["allAccounts"]),
  components: {
    MyFavorite,
  },
  methods: {
    showAccount(accNumber) {
      //TODO: bytt til konto view
      console.log(accNumber);
    },
  },
};
</script>

<style></style>
