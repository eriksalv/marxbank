<template>
  <div class="flex flex-row flex-wrap min-w-[80%] justify-center">
    <AccountForm />
    <AccountList class="min-w-max" />
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
  async created() {
    await this.fetchAccounts();
  },
  methods: {
    ...mapActions(["fetchAccounts"]),
  },
};
</script>
