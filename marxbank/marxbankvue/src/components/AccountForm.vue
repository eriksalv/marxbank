<template>
  <div class="w-1/4">
    <Alert :message="successMsg" @onHideAlert="successMsg = null" />
    <ErrorAlert :message="errorMsg" @onHideAlert="errorMsg = null" />
    <h1 class="title">Ny konto</h1>
    <h1>Kontonavn</h1>
    <input
      ref="accountName"
      type="text"
      className="input"
      placeholder="Ny konto" />
    <h1>Kontotype</h1>
    <select
      id="accountTypes"
      ref="accountType"
      class="select"
      name="accountTypes">
      <option value="Brukskonto">Brukskonto</option>
      <option value="Sparekonto">Sparekonto</option>
      <option value="Marxkonto">Marxkonto</option>
      <option value="Kredittkonto">Kredittkonto</option>
    </select>
    <button class="button" @click="createAccountWrapper">Opprett konto</button>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import Alert from "../components/global/Alert.vue";
import ErrorAlert from "../components/global/ErrorAlert.vue";
export default {
  name: "AccountForm",
  components: {
    Alert,
    ErrorAlert,
  },
  data() {
    return {
      errorMsg: null,
      successMsg: null,
    };
  },
  methods: {
    ...mapActions(["createAccount"]),
    createAccountWrapper() {
      this.errorMsg = null;
      this.successMsg = null;
      const request = {
        name: this.$refs.accountName.value,
        type: this.$refs.accountType.value,
      };
      this.createAccount(request)
        .then(() => {
          this.successMsg = "Konto ble opprettet";
        })
        .catch((err) => {
          this.errorMsg = err.message;
        });
    },
  },
};
</script>

<style></style>
