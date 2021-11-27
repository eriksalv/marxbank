<template>
  <div>
    <div v-if="filterAccountsByUserId(getUserId).length">
      <MyFavorite
        v-for="account in filterAccountsByUserId(getUserId).slice(0, 3)"
        :key="account.id"
        :balance="account.balance"
        :acc-name="account.name"
        :type="account.type"
        :id="account.id"
        class="account"
        @click="showAccount(account.id)" />
    </div>
    <div v-else>
      <CreateAccountPath />
    </div>
  </div>
</template>

<script>
import MyFavorite from "./MyFavorite.vue";
import CreateAccountPath from "./CreateAccountPath.vue";
import { mapGetters } from "vuex";

export default {
  name: "MyFavorites",
  components: {
    MyFavorite,
    CreateAccountPath,
  },
  computed: mapGetters(["filterAccountsByUserId", "getUserId"]),
  methods: {
    showAccount(id) {
      this.$router.push({ name: "AccountInfo", params: { id: id } });
    },
  },
};
</script>

<style></style>
