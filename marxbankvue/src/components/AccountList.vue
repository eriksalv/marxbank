<template>
  <div class="w-full overflow-auto h-80">
    <table class="w-full border-collapse">
      <thead>
        <tr
          class="
            text-xs
            font-semibold
            tracking-wide
            text-center text-gray-800
            uppercase
            border-b
            dark:border-gray-700
            bg-gray-50
            dark:text-gray-400 dark:bg-gray-800
          ">
          <th class="th px-4 py-3">Kontonavn</th>
          <th class="th px-4 py-3">Disp. beløp</th>
          <th class="th px-4 py-3">Kontotype</th>
          <th class="th px-4 py-3">Id</th>
        </tr>
      </thead>
      <tbody class="bg-white divide-y dark:divide-gray-700 dark:bg-gray-800">
        <Account
          v-for="account in filterAccountsByUserId(getUserId)"
          :id="account.id"
          :key="account.id"
          :balance="account.balance"
          :acc-name="account.name"
          :type="account.type"
          class="account"
          @accountSelected="showAccount" />
      </tbody>
    </table>
  </div>
</template>

<script>
import Account from "./Account.vue";
import { mapGetters } from "vuex";

export default {
  name: "AccountList",
  components: {
    Account,
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
