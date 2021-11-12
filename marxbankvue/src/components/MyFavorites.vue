<template>
  <div>
    <MyFavorite
      @click="showAccount(account.accNumber)"
      v-for="account in allAccounts.slice(0, 3)"
      :key="account.id"
      :accNumber="account.accNumber"
      :balance="account.balance"
      :accName="account.name"
      :type="account.type"
      class="account" />
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
