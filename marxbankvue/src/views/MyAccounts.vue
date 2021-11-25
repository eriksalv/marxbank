<template>
  <div class="flex flex-row flex-wrap min-w-[80%] justify-center">
    <AccountList class="min-w-max" />
    <AccountForm />
  </div>
</template>

<script>
// @ is an alias to /src
import AccountList from "../components/AccountList.vue";
import AccountForm from "../components/AccountForm.vue";
import { mapActions, mapGetters } from "vuex";

export default {
  name: "MyAccounts",
  components: {
    AccountList,
    AccountForm,
  },
  computed: {
    ...mapGetters(["accountStatus"]),
  },
  methods: {
    ...mapActions(["fetchAccounts"]),
  },
  async created() {
    await this.fetchAccounts();
  },
};
</script>
